package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.User;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    @Step("login")
    public static Response login(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Api.AUTH_LOGIN);
    }

}
