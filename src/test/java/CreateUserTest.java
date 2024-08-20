import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.Tokens;
import org.example.User;
import org.junit.After;
import org.junit.Test;
import steps.Api;
import steps.CreateUserSteps;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CreateUserTest extends BaseTest {

    private final ArrayList<Tokens> createdUsersTokens = new ArrayList<>();

    @Test
    @DisplayName("создать уникального пользователя")
    public void createUniqueUserTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        Response response = CreateUserSteps.createUser(user);
        Tokens tokens = Api.gson.fromJson(response.asString(), Tokens.class);
        createdUsersTokens.add(tokens);
        response
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("success", is(true));

    }

    @Test
    @DisplayName("создать пользователя, который уже зарегистрирован;")
    public void createAlreadyRegisteredUserTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        Response response = CreateUserSteps.createUser(user);
        Tokens tokens = Api.gson.fromJson(response.asString(), Tokens.class);
        createdUsersTokens.add(tokens);
        Response response2 = CreateUserSteps.createUser(user);
        response2
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat().body("success", is(false))
                .and()
                .assertThat().body("message", is(MESSAGE_ON_EXIST));
    }

    @Test
    @DisplayName("создать пользователя без email")
    public void createUserWithoutEmailTest() {
        User user = new User(
                "",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        Response response = CreateUserSteps.createUser(user);
        response
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat().body("success", is(false))
                .and()
                .assertThat().body("message", equalTo(MESSAGE_ON_ANY_EMPTY_REQUIRED));
    }

    @Test
    @DisplayName("создать пользователя без имени")
    public void createUserWithoutNameTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                "",
                UUID.randomUUID().toString()
        );
        Response response = CreateUserSteps.createUser(user);
        response
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat().body("success", is(false))
                .and()
                .assertThat().body("message", equalTo(MESSAGE_ON_ANY_EMPTY_REQUIRED));
    }

    @Test
    @DisplayName("создать пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        User user = new User(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                ""
        );
        Response response = CreateUserSteps.createUser(user);
        response
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat().body("success", is(false))
                .and()
                .assertThat().body("message", equalTo(MESSAGE_ON_ANY_EMPTY_REQUIRED));
    }

    @After
    public void tearDown() {
        for (Tokens tokens : createdUsersTokens) {
            CreateUserSteps.deleteUser(tokens);
        }
        createdUsersTokens.clear();
    }

    private final static String MESSAGE_ON_EXIST = "User already exists";
    private final static String MESSAGE_ON_ANY_EMPTY_REQUIRED = "Email, password and name are required fields";
}
