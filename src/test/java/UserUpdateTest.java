import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.User;
import org.junit.Before;
import org.junit.Test;
import steps.UserUpdateSteps;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class UserUpdateTest extends BaseTest {

    @Before
    public void setUp() {
        createUser();
    }

    @Test
    @DisplayName("Изменение данных с авторизацией")
    public void updateWithAuthEmailTest() {
        User userUpdated = new User(
                UUID.randomUUID() + "@example.com",
                user.getName(),
                user.getPassword()
        );
        Response response = UserUpdateSteps.update(createdUserTokens.getAccessToken(), userUpdated);
        response.then().statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("success", is(true))
                .and()
                .assertThat().body("user.name", equalTo(userUpdated.getName()))
                .and()
                .assertThat().body("user.email", equalTo(userUpdated.getEmail()));
    }

    @Test
    @DisplayName("Изменение данных без авторизации")
    public void updateWithoutAuthTokenTest() {
        Response response = UserUpdateSteps.update(
                null,
                new User(
                        UUID.randomUUID() + "@example.com",
                        user.getName(),
                        user.getPassword()
                )
        );
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and()
                .assertThat().body("success", is(false))
                .and()
                .assertThat().body("message", equalTo(MESSAGE_ON_ERROR));
    }

    private final static String MESSAGE_ON_ERROR = "You should be authorised";
}

