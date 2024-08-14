import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {
    @Step("make create user request")
    public static Response createUserResponse(ApiUser user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Api.AUTH_REGISTER);
    }

    @Step("create unique user")
    public static ApiTokens createUniqueUser(ApiUser user) {
        Response response = createUserResponse(user);
        CommonSteps.validateStatus(response, 200);
        CommonSteps.validateSuccess(response, true);
        return Api.gson.fromJson(response.asString(), ApiTokens.class);
    }

    @Step("create non unique user")
    public static void createNonUniqueUser(ApiUser user) {
        Response response = createUserResponse(user);
        CommonSteps.validateStatus(response, 403);
        CommonSteps.validateSuccess(response, false);
        CommonSteps.validateMessage(response, MESSAGE_ON_EXIST);
    }

    @Step("create user with some empty required field")
    public static void createUserWithEmptyFields(ApiUser user) {
        Response response = createUserResponse(user);
        CommonSteps.validateStatus(response, 403);
        CommonSteps.validateSuccess(response, false);
        CommonSteps.validateMessage(response, MESSAGE_ON_ANY_EMPTY_REQUIRED);
    }

    @Step("delete user")
    public static void deleteUser(ApiTokens tokens) {
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", tokens.getAccessToken())
                .delete(Api.AUTH_USER);
    }

    private final static String MESSAGE_ON_EXIST = "User already exists";
    private final static String MESSAGE_ON_ANY_EMPTY_REQUIRED = "Email, password and name are required fields";
}
