package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.BodyCreateOrder;
import org.example.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateOrderSteps {

    @Step("get ingredients")
    public static Response getIngredients(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .get(Api.INGREDIENTS);
    }

    @Step("create order")
    public static Response createOrder(String accessToken, List<Ingredient> ingredients) {
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
}
