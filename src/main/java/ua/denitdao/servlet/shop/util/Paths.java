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

    public static final String POST_PROFILE = BASE + Commands.POST_PROFILE;
    public static final String VIEW_PROFILE = BASE + Commands.VIEW_PROFILE;

    public static final String POST_ADD_PRODUCT = BASE + Commands.POST_ADD_PRODUCT;
    public static final String VIEW_ADD_PRODUCT = BASE + Commands.VIEW_ADD_PRODUCT;

    public static final String SOME = BASE + Commands.SOME;
    public static final String DEFAULT = BASE + Commands.DEFAULT;

    public static final String LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
    public static final String REGISTER_JSP = "/WEB-INF/jsp/register.jsp";
    public static final String HOME_JSP = "/WEB-INF/jsp/home.jsp";
    public static final String PRODUCT_JSP = "/WEB-INF/jsp/product.jsp";
    public static final String CATEGORY_JSP = "/WEB-INF/jsp/category.jsp";
    public static final String PROFILE_JSP = "/WEB-INF/jsp/profile.jsp";
    public static final String ERROR_JSP = "/WEB-INF/jsp/error.jsp";

    private Paths() {
    }
}
