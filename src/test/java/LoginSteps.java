import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
import org.hamcrest.CoreMatchers;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    @Step("login")
    public static void login(User user) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Api.AUTH_LOGIN);
        CommonSteps.validateStatus(response, 200);
        CommonSteps.validateSuccess(response, true);
        response.then().body("user", CoreMatchers.notNullValue());
    }

    @Step("login")
    public static void loginWrongCredentials(User user) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Api.AUTH_LOGIN);
        CommonSteps.validateStatus(response, 401);
        CommonSteps.validateSuccess(response, false);
        CommonSteps.validateMessage(response, MESSAGE_ON_ERROR);
    }

    private final static String MESSAGE_ON_ERROR = "email or password are incorrect";
}
