import io.restassured.RestAssured;
import model.Tokens;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class LoginTest {

    private final User user = new User(
        UUID.randomUUID() + "@example.com",
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
    );

    private Tokens createdUserTokens;

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
        createdUserTokens = CreateUserSteps.createUniqueUser(user);
    }

    @Test
    public void loginExistUserTest() {
        LoginSteps.login(user);
    }

    @Test
    public void loginNonExistUserTest() {
        User notExistUser = new User(user.getEmail(), null, "");
        LoginSteps.loginWrongCredentials(notExistUser);
    }

    @After
    public void tearDown() {
        CreateUserSteps.deleteUser(createdUserTokens);
    }
}

