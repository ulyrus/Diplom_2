import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class LoginTest {

    private final ApiUser user = new ApiUser(
        UUID.randomUUID() + "@example.com",
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
    );

    private ApiTokens createdUserTokens;

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
        ApiUser notExistUser = new ApiUser(user.getEmail(), null, "");
        LoginSteps.loginWrongCredentials(notExistUser);
    }

    @After
    public void tearDown() {
        CreateUserSteps.deleteUser(createdUserTokens);
    }
}

