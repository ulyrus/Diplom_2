package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.Tokens;
import org.example.User;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {
    @Step("create user")
    public static Response createUser(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Api.AUTH_REGISTER);
    }

    @Step("delete user")
    public static void deleteUser(Tokens tokens) {
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", tokens.getAccessToken())
                .delete(Api.AUTH_USER);
    }
}
