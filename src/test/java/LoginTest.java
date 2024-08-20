import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.example.User;
import org.junit.Before;
import org.junit.Test;
import steps.LoginSteps;

import static org.hamcrest.CoreMatchers.*;

public class LoginTest extends BaseTest {

    @Before
    public void setUp() {
       createUser();
    }

    @Test
    @DisplayName("логин под существующим пользователем")
    public void loginExistUserTest() {
        LoginSteps.login(user)
                .then().statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("success", is(true))
                .and()
                .assertThat().body("user", notNullValue());
    }

    @Test
    @DisplayName("логин с неверным логином и паролем")
    public void loginNonExistUserTest() {
        User notExistUser = new User(user.getEmail(), null, "");
        LoginSteps.login(notExistUser)
                .then().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and()
                .assertThat().body("success", is(false))
                .and()
                .assertThat().body("message", equalTo(MESSAGE_ON_ERROR));
    }

    private final static String MESSAGE_ON_ERROR = "email or password are incorrect";
}

