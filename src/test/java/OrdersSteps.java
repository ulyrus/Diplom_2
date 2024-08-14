import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class OrdersSteps {

    @Step("get orders")
    private static void getOrders(ApiTokens tokens) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if (tokens != null) {
            headers.put("Authorization",  tokens.getAccessToken());
        }
        Response response = given()
                .headers(headers)
                .get(Api.ORDERS_ALL);
        CommonSteps.validateStatus(response, 200);
        CommonSteps.validateSuccess(response, true);
        CommonSteps.validateArray(response, "orders");
        CommonSteps.validateHasField(response, "total");
        CommonSteps.validateIsInt(response, "total");
        CommonSteps.validateHasField(response, "totalToday");
        CommonSteps.validateIsInt(response, "totalToday");
    }

    @Step("get orders with token")
    public static void getOrdersWithToken(ApiTokens tokens) {
        getOrders(tokens);
    }

    @Step("get orders without token")
    public static void getOrdersWithoutToken() {
        getOrders(null);
    }
}
