import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.ResponseUserUpdate;
import model.Tokens;
import model.User;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class UserUpdateSteps {

    @Step("update with token")
    public static void updateWithToken(Tokens tokens, User userUpdate) {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", tokens.getAccessToken())
                .body(userUpdate)
                .patch(Api.AUTH_USER);
        CommonSteps.validateStatus(response, 200);
        CommonSteps.validateSuccess(response, true);
        User newUser = Api.gson.fromJson(response.asString(), ResponseUserUpdate.class).getUser();
        Assert.assertEquals(userUpdate.getName(), newUser.getName());
        Assert.assertEquals(userUpdate.getEmail(), newUser.getEmail());
    }

    @Step("update without token")
    public static void updateWithoutToken(User user) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(user)
                .patch(Api.AUTH_USER);
        CommonSteps.validateStatus(response, 401);
        CommonSteps.validateSuccess(response, false);
        CommonSteps.validateMessage(response, MESSAGE_ON_ERROR);
    }

    private final static String MESSAGE_ON_ERROR = "You should be authorised";
}
