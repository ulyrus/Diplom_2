import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class UserUpdateTest {

    private final ApiUser user = new ApiUser(
            UUID.randomUUID() + "@example.com",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
    );
    private ApiTokens tokens;

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
        tokens = CreateUserSteps.createUniqueUser(user);
    }

    @Test
    public void updateWithAuthEmailTest() {
        UserUpdateSteps.updateWithToken(
            tokens,
            new ApiUser(
                UUID.randomUUID() + "@example.com",
                user.getName(),
                user.getPassword()
            )
        );
    }

    @Test
    public void updateWithAuthNameTest() {
        UserUpdateSteps.updateWithToken(
            tokens,
            new ApiUser(
                user.getEmail(),
                    UUID.randomUUID().toString(),
                user.getPassword()
            )
        );
    }

    @Test
    public void updateWithAuthPasswordTest() {
        UserUpdateSteps.updateWithToken(
            tokens,
            new ApiUser(
                user.getEmail(),
                user.getName(),
                    UUID.randomUUID().toString()
            )
        );
    }

    @Test
    public void updateWithoutTokenAuthEmailTest() {
        UserUpdateSteps.updateWithoutToken(
                new ApiUser(
                        UUID.randomUUID() + "@example.com",
                        user.getName(),
                        user.getPassword()
                )
        );
    }

    @Test
    public void updateWithoutTokenAuthNameTest() {
        UserUpdateSteps.updateWithoutToken(
                new ApiUser(
                        user.getEmail(),
                        UUID.randomUUID().toString(),
                        user.getPassword()
                )
        );
    }

    @Test
    public void updateWithoutTokenAuthPasswordTest() {
        UserUpdateSteps.updateWithoutToken(
                new ApiUser(
                        user.getEmail(),
                        user.getName(),
                        UUID.randomUUID().toString()
                )
        );
    }

    @After
    public void tearDown() {
        CreateUserSteps.deleteUser(tokens);
    }
}

