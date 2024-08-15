import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.BodyCreateOrder;
import model.Ingredient;
import model.ResponseIngredients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class CreateOrderSteps {

    @Step("get ingredients")
    public static List<Ingredient> getIngredients(String accessToken) {
        Response response = given()
                .header("Authorization", accessToken)
                .get(Api.INGREDIENTS);
        CommonSteps.validateStatus(response, 200);
        CommonSteps.validateSuccess(response, true);
        CommonSteps.validateArray(response, "data");
        return Api.gson.fromJson(response.body().asString(), ResponseIngredients.class).getData();
    }

    @Step("create order request")
    public static Response createOrderRequest(String accessToken, List<Ingredient> ingredients) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if (accessToken != null) {
            headers.put("Authorization", accessToken);
        }
        ArrayList<String> ingredientsList = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientsList.add(ingredient.get_id());
        }
        return given()
                .headers(headers)
                .body(new BodyCreateOrder(ingredientsList))
                .post(Api.ORDERS);
    }

    @Step("create order without authorization")
    public static void createOrderWithoutAuthorization(List<Ingredient> ingredients) {
        Response response = createOrderRequest(null, ingredients);
        CommonSteps.validateStatus(response, 200);
        CommonSteps.validateSuccess(response, true);
        CommonSteps.validateHasField(response, "order.number");
        CommonSteps.validateIsInt(response, "order.number");
    }

    @Step("create order with authorization")
    public static void createOrderWithAuthorization(String accessToken, List<Ingredient> ingredients) {
        Response response = createOrderRequest(accessToken, ingredients);
        CommonSteps.validateStatus(response, 200);
        CommonSteps.validateSuccess(response, true);
        CommonSteps.validateHasField(response, "order.number");
        CommonSteps.validateIsInt(response, "order.number");
    }

    @Step("create order without ingredients")
    public static void createOrderWithoutIngredients(String accessToken) {
        Response response = createOrderRequest(accessToken, new ArrayList<>());
        CommonSteps.validateStatus(response, 400);
        CommonSteps.validateSuccess(response, false);
        CommonSteps.validateMessage(response, MESSAGE_ON_ERROR);
    }

    @Step("create order with wrong ingredients")
    public static void createOrderWithWrongIngredients(String accessToken) {
        Response response = createOrderRequest(accessToken, List.of(new Ingredient(UUID.randomUUID().toString())));
        CommonSteps.validateStatus(response, 500);
    }

    private final static String MESSAGE_ON_ERROR = "Ingredient ids must be provided";
}
