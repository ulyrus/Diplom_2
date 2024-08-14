import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrdersTest {

    private ApiTokens tokens;

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
        tokens = CreateUserSteps.createUniqueUser(
            new ApiUser(
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

