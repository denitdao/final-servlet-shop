package ua.denitdao.servlet.shop.util;

public final class Commands {
    // guest
    public static final String POST_LOGIN = "postLogin";
    public static final String VIEW_LOGIN = "login";
    public static final String POST_REGISTER = "postRegister";
    public static final String VIEW_REGISTER = "register";
    public static final String VIEW_HOME = "home";
    public static final String VIEW_CATEGORY = "category";
    public static final String VIEW_PRODUCT = "product";
    // user
    public static final String POST_LOGOUT = "postLogout";
    public static final String VIEW_PROFILE = "profile";
    public static final String POST_PROFILE = "postProfile";
    // admin
    public static final String POST_ADD_PRODUCT = "postAddProduct";
    public static final String VIEW_ADD_PRODUCT = "addProduct";
    public static final String POST_UPDATE_PRODUCT = "postUpdateProduct";
    public static final String VIEW_UPDATE_PRODUCT = "updateProduct";
    public static final String POST_DELETE_PRODUCT = "postDeleteProduct";
    // other
    public static final String DEFAULT = "default";
    public static final String SOME = "some"; // stub

    private Commands() {
    }
}
