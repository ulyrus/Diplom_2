import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.Ingredient;
import org.example.ResponseIngredients;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import steps.CreateOrderSteps;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CreateOrderTest extends BaseTest {

    private List<Ingredient> ingredients;

    @Before
    public void setUp() {
        createUser();
        Response response = CreateOrderSteps.getIngredients(createdUserTokens.getAccessToken());
        ingredients = gson.fromJson(response.body().asString(), ResponseIngredients.class).getData();
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderNotAuthorizedWithIngredientsTest() {
        CreateOrderSteps.createOrder(createdUserTokens.getAccessToken(), ingredients)
                .then().statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("success", is(true))
                .and()
                .assertThat().body("order.number",  Is.isA(Integer.class));
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void createOrderWithIngredientsTest() {
        CreateOrderSteps.createOrder(null, ingredients)
                .then().statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("success", is(true))
                .and()
                .assertThat().body("order.number",  Is.isA(Integer.class));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        CreateOrderSteps.createOrder(createdUserTokens.getAccessToken(), new ArrayList<>())
                .then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat().body("success", is(false))
                .and()
                .assertThat().body("message", equalTo(MESSAGE_ON_ERROR));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithWrongIngredientsTest() {
        CreateOrderSteps.createOrder(createdUserTokens.getAccessToken(), List.of(new Ingredient(UUID.randomUUID().toString())))
                .then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    private final static String MESSAGE_ON_ERROR = "Ingredient ids must be provided";
}

