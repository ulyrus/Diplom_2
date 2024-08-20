package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class OrdersSteps {

    @Step("get orders")
    public static Response getOrders(String accessToken) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if (accessToken != null) {
            headers.put("Authorization", accessToken);
        }
        return given()
                .headers(headers)
                .get(Api.ORDERS_ALL);
    }
}
