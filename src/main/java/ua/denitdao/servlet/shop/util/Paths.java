package ua.denitdao.servlet.shop.util;

public final class Paths {

    private static final String BASE = "/shop/";

    public static final String POST_LOGIN = BASE + Commands.POST_LOGIN;
    public static final String VIEW_LOGIN = BASE + Commands.VIEW_LOGIN;
    public static final String POST_REGISTER = BASE + Commands.POST_REGISTER;
    public static final String VIEW_REGISTER = BASE + Commands.VIEW_REGISTER;

    public static final String POST_LOGOUT = BASE + Commands.POST_LOGOUT;

    public static final String VIEW_HOME = BASE + Commands.VIEW_HOME;
    public static final String VIEW_CATEGORY = BASE + Commands.VIEW_CATEGORY;
    public static final String VIEW_PRODUCT = BASE + Commands.VIEW_PRODUCT;
    public static final String POST_ADD_TO_CART = BASE + Commands.POST_ADD_TO_CART;

    public static final String POST_EDIT_USER = BASE + Commands.POST_EDIT_USER;
    public static final String VIEW_ALL_USERS = BASE + Commands.VIEW_ALL_USERS;
    public static final String VIEW_CART = BASE + Commands.VIEW_CART;
    public static final String POST_ADD_ORDER = BASE + Commands.POST_ADD_ORDER;
    public static final String VIEW_ALL_ORDERS = BASE + Commands.VIEW_ALL_ORDERS;

    public static final String POST_ADD_PRODUCT = BASE + Commands.POST_ADD_PRODUCT;
    public static final String VIEW_ADD_PRODUCT = BASE + Commands.VIEW_ADD_PRODUCT;
    public static final String POST_UPDATE_PRODUCT = BASE + Commands.POST_UPDATE_PRODUCT;
    public static final String VIEW_UPDATE_PRODUCT = BASE + Commands.VIEW_UPDATE_PRODUCT;
    public static final String POST_DELETE_PRODUCT = BASE + Commands.POST_DELETE_PRODUCT;
    public static final String POST_UPDATE_ORDER = BASE + Commands.POST_UPDATE_ORDER;
    public static final String VIEW_ORDER = BASE + Commands.VIEW_ORDER;

    public static final String DEFAULT = BASE + Commands.DEFAULT;

    public static final String LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
    public static final String REGISTER_JSP = "/WEB-INF/jsp/register.jsp";
    public static final String HOME_JSP = "/WEB-INF/jsp/home.jsp";
    public static final String PRODUCT_JSP = "/WEB-INF/jsp/product.jsp";
    public static final String ADD_PRODUCT_JSP = "/WEB-INF/jsp/admin/add_product.jsp";
    public static final String CATEGORY_JSP = "/WEB-INF/jsp/category.jsp";
    public static final String CART_JSP = "/WEB-INF/jsp/user/cart.jsp";
    public static final String ORDER_JSP = "/WEB-INF/jsp/admin/order.jsp";
    public static final String ALL_ORDERS_JSP = "/WEB-INF/jsp/user/all_orders.jsp";
    public static final String ALL_USERS_JSP = "/WEB-INF/jsp/admin/all_users.jsp";
    public static final String UPDATE_PRODUCT_JSP = "/WEB-INF/jsp/admin/update_product.jsp";
    public static final String ERROR_JSP = "/WEB-INF/jsp/error.jsp";

    public static final String IMAGES = "/resources/images";

    private Paths() {
    }
}
