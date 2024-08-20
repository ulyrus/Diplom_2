import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Tokens;
import org.example.User;
import org.junit.After;
import org.junit.Before;
import steps.Api;
import steps.CreateUserSteps;

import java.util.UUID;

public class BaseTest {

    protected Gson gson = new Gson();

    protected final User user = new User(
            UUID.randomUUID() + "@example.com",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
    );

    protected Tokens createdUserTokens;

    @Before
    public void baseSetUp() {
        RestAssured.baseURI = Api.BASE_URL;
    }

    protected void createUser() {
        Response response = CreateUserSteps.createUser(user);
        createdUserTokens = gson.fromJson(response.asString(), Tokens.class);
    }

    @After
    public void baseTearDown() {
        if (createdUserTokens != null) {
            CreateUserSteps.deleteUser(createdUserTokens);
        }
    }
}
