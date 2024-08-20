package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.User;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UserUpdateSteps {

    @Step("update with token")
    public static Response update(String accessToken, User userUpdate) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if (accessToken != null) {
            headers.put("Authorization", accessToken);
        }
        Response response = given()
                .headers(headers)
                .body(userUpdate)
                .patch(Api.AUTH_USER);
        return response;
    }
}
