package userstoreauth;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Constants.
 */
public final class Const {
    /** Session attributes. */
    public static final String CURRENT_USER = "user";
    /** Roles. */
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    /** Cookie attributes. */
    public static final String SAVE = "saved_session";
    /** JSP. */
    public static final String LOGIN_JSP = "/WEB-INF/auth/Login.jsp";
    public static final String EDIT_JSP = "/WEB-INF/auth/EditUser.jsp";
    public static final String USER_ALL_JSP = "/WEB-INF/auth/UsersViewAuth.jsp";
    public static final String ADMIN_ALL_JSP = "/WEB-INF/auth/UsersViewAdmin.jsp";
    public static final String ADD_ERR_JSP = "/WEB-INF/jstltest/AddError.jsp";

    private Const() {
    }
}
