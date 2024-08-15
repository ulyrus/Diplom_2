import io.restassured.RestAssured;
import model.Tokens;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrdersTest {

    private Tokens tokens;

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
        tokens = CreateUserSteps.createUniqueUser(
            new User(
                UUID.randomUUID() + "@example.com",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
            )
        );
    }

    @Test
    public void getOrdersAuthorizedTest() {
        OrdersSteps.getOrdersWithToken(tokens);
    }

    @Test
    public void getOrdersNotAuthorizedTest() {
        OrdersSteps.getOrdersWithoutToken();
    }

    @After
    public void tearDown() {
        CreateUserSteps.deleteUser(tokens);
    }
}

