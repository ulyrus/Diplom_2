import io.restassured.RestAssured;
import model.Tokens;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

public class CreateUserTest {

    private final ArrayList<Tokens> createdUsersTokens = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
    }

    @Test
    public void createUniqueUserTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        Tokens tokens = CreateUserSteps.createUniqueUser(user);
        createdUsersTokens.add(tokens);
    }

    @Test
    public void createAlreadyRegisteredUserTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        Tokens tokens = CreateUserSteps.createUniqueUser(user);
        createdUsersTokens.add(tokens);
        CreateUserSteps.createNonUniqueUser(user);
    }

    @Test
    public void createUserWithoutEmailTest() {
        User user = new User(
                "",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        CreateUserSteps.createUserWithEmptyFields(user);
    }

    @Test
    public void createUserWithoutNameTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                "",
                UUID.randomUUID().toString()
        );
        CreateUserSteps.createUserWithEmptyFields(user);
    }

    @Test
    public void createUserWithoutPasswordTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                ""
        );
        CreateUserSteps.createUserWithEmptyFields(user);
    }

    @After
    public void tearDown() {
        for (Tokens tokens : createdUsersTokens) {
            CreateUserSteps.deleteUser(tokens);
        }
        createdUsersTokens.clear();
    }
}
