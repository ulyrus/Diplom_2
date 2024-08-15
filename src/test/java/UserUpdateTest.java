import io.restassured.RestAssured;
import model.Tokens;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class UserUpdateTest {

    private final User user = new User(
            UUID.randomUUID() + "@example.com",
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
    );
    private Tokens tokens;

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
        tokens = CreateUserSteps.createUniqueUser(user);
    }

    @Test
    public void updateWithAuthEmailTest() {
        UserUpdateSteps.updateWithToken(
            tokens.getAccessToken(),
            new User(
                UUID.randomUUID() + "@example.com",
                user.getName(),
                user.getPassword()
            )
        );
    }

    @Test
    public void updateWithAuthNameTest() {
        UserUpdateSteps.updateWithToken(
            tokens.getAccessToken(),
            new User(
                user.getEmail(),
                    UUID.randomUUID().toString(),
                user.getPassword()
            )
        );
    }

    @Test
    public void updateWithAuthPasswordTest() {
        UserUpdateSteps.updateWithToken(
            tokens.getAccessToken(),
            new User(
                user.getEmail(),
                user.getName(),
                    UUID.randomUUID().toString()
            )
        );
    }

    @Test
    public void updateWithoutTokenAuthEmailTest() {
        UserUpdateSteps.updateWithoutToken(
                new User(
                        UUID.randomUUID() + "@example.com",
                        user.getName(),
                        user.getPassword()
                )
        );
    }

    @Test
    public void updateWithoutTokenAuthNameTest() {
        UserUpdateSteps.updateWithoutToken(
                new User(
                        user.getEmail(),
                        UUID.randomUUID().toString(),
                        user.getPassword()
                )
        );
    }

    @Test
    public void updateWithoutTokenAuthPasswordTest() {
        UserUpdateSteps.updateWithoutToken(
                new User(
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

