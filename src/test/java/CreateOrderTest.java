import io.restassured.RestAssured;
import model.Ingredient;
import model.Tokens;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class CreateOrderTest {

    private final User user = new User(
        UUID.randomUUID() + "@example.com",
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
    );

    private Tokens createdUserTokens;

    private List<Ingredient> ingredients;

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.BASE_URL;
        createdUserTokens = CreateUserSteps.createUniqueUser(user);
        ingredients = CreateOrderSteps.getIngredients(createdUserTokens.getAccessToken());
    }

    @Test
    public void createOrderNotAuthorizedWithIngredientsTest() {
        CreateOrderSteps.createOrderWithoutAuthorization(ingredients);
    }

    @Test
    public void createOrderWithIngredientsTest() {
        CreateOrderSteps.createOrderWithAuthorization(createdUserTokens.getAccessToken(), ingredients);
    }

    @Test
    public void createOrderWithoutIngredientsTest() {
        CreateOrderSteps.createOrderWithoutIngredients(createdUserTokens.getAccessToken());
    }

    @Test
    public void createOrderWithWrongIngredientsTest() {
        CreateOrderSteps.createOrderWithWrongIngredients(createdUserTokens.getAccessToken());
    }

    @After
    public void tearDown() {
        CreateUserSteps.deleteUser(createdUserTokens);
    }
}

