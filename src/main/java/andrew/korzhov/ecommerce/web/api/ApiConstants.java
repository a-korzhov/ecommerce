package andrew.korzhov.ecommerce.web.api;

public final class ApiConstants {

    public static final String API_PREFIX = "/api";

    // USER
    public static final String USERS_API = API_PREFIX + "/users";
    public static final String USER_CATEGORIES = API_PREFIX + "/categories";
    public static final String USER_PRODUCTS = API_PREFIX + "/products";
    public static final String USER_CART_ITEM = API_PREFIX + "/cart";
    public static final String USER_ORDER_ENTRY = API_PREFIX + "/entries";
    public static final String USER_ORDER = API_PREFIX + "/orders";

    // ADMIN
    public static final String ADMIN_API_PREFIX = API_PREFIX + "/admin";
    public static final String ADMIN_PRODUCTS = ADMIN_API_PREFIX + "/products";
    public static final String ADMIN_CATEGORIES = ADMIN_API_PREFIX + "/categories";

}
