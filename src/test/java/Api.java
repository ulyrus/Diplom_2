import com.google.gson.Gson;

public class Api {
    public static String BASE_URL = "https://stellarburgers.nomoreparties.site/api";
    public static String ORDERS = "orders";
    public static String AUTH_REGISTER = "auth/register";
    public static String AUTH_USER = "auth/user";
    public static String AUTH_LOGIN = "auth/login";
    public static String ORDERS_ALL = "orders/all";

    public static Gson gson = new Gson();
}
