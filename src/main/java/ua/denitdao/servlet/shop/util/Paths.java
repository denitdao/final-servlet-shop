package ua.denitdao.servlet.shop.util;

public final class Paths {

    private static final String BASE = "/c/";

    public static final String LOGIN = BASE + Commands.LOGIN;
    public static final String VIEW_LOGIN = BASE + Commands.VIEW_LOGIN;
    public static final String LOGOUT = BASE + Commands.LOGOUT;
    public static final String VIEW_HOME = BASE + Commands.VIEW_HOME;

    public static final String SOME = BASE + Commands.SOME;
    public static final String DEFAULT = BASE + Commands.DEFAULT;

    public static final String LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
    public static final String HOME_JSP = "/WEB-INF/jsp/home.jsp";
    public static final String ERROR_JSP = "/WEB-INF/jsp/error.jsp";

    private Paths() {
    }
}
