import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.core.IsIterableContaining;
import org.junit.Before;
import org.junit.Test;
import steps.OrdersSteps;

import static org.hamcrest.CoreMatchers.*;

public class OrdersTest extends BaseTest {

    @Before
    public void setUp() {
        createUser();
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void getOrdersAuthorizedTest() {
        validateResponse(OrdersSteps.getOrders(createdUserTokens.getAccessToken()));
    }

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void getOrdersNotAuthorizedTest() {
        validateResponse(OrdersSteps.getOrders(null));
    }

    private void validateResponse(Response response) {
        response
                .then().statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("success", is(true))
                .and()
                .assertThat().body("orders", IsIterableContaining.hasItems(notNullValue()))
                .and()
                .assertThat().body("total", isA(Integer.class))
                .and()
                .assertThat().body("totalToday", isA(Integer.class));
    }

}

