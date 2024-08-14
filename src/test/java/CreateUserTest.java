import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

public class CreateUserTest {

    private final ArrayList<ApiTokens> createdUsersTokens = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
    }

    @Test
    public void createUniqueUserTest() {
        ApiUser user = new ApiUser(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        ApiTokens tokens = CreateUserSteps.createUniqueUser(user);
        createdUsersTokens.add(tokens);
    }

    @Test
    public void createAlreadyRegisteredUserTest() {
        ApiUser user = new ApiUser(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        ApiTokens tokens = CreateUserSteps.createUniqueUser(user);
        createdUsersTokens.add(tokens);
        CreateUserSteps.createNonUniqueUser(user);
    }

    @Test
    public void createUserWithoutEmailTest() {
        ApiUser user = new ApiUser(
                "",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        CreateUserSteps.createUserWithEmptyFields(user);
    }

    @Test
    public void createUserWithoutNameTest() {
        ApiUser user = new ApiUser(
                UUID.randomUUID() + "@example.com",
                "",
                UUID.randomUUID().toString()
        );
        CreateUserSteps.createUserWithEmptyFields(user);
    }

    @Test
    public void createUserWithoutPasswordTest() {
        ApiUser user = new ApiUser(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                ""
        );
        CreateUserSteps.createUserWithEmptyFields(user);
    }

    @After
    public void tearDown() {
        for (ApiTokens tokens : createdUsersTokens) {
            CreateUserSteps.deleteUser(tokens);
        }
        createdUsersTokens.clear();
    }
}
