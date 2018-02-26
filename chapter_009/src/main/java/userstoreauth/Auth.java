package userstoreauth;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Constants.
 */
final class Auth {
    /** Session attributes. */
    static final String CURRENT_USER = "user";
    /** Roles. */
    static final String USER = "user";
    static final String ADMIN = "admin";
    /** Cookie attributes. */
    static final String SAVE = "saved_session";
    /** JSP. */
    static final String LOGIN_JSP = "/WEB-INF/auth/Login.jsp";
    static final String EDIT_JSP = "/WEB-INF/auth/EditUser.jsp";
    static final String USER_ALL_JSP = "/WEB-INF/auth/UsersViewAuth.jsp";
    static final String ADMIN_ALL_JSP = "/WEB-INF/auth/UsersViewAdmin.jsp";
    static final String ADD_ERR_JSP = "/WEB-INF/jstltest/AddError.jsp";

    private Auth() {
    }
}
