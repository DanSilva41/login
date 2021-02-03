package br.com.felipe.login.support;

/**
 * Application constants
 *
 * @author Danilo Silva
 */
public final class Constants {

    //Regex para logins aceitáveis
    public static final String EMAIL_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String ADMIN_ACCOUNT = "admin";
    public static final String ANONYMOUS_USER = "anonimo";
    public static final String DEFAULT_PASSWORD = "def@ult_Password";

    private Constants() {
    }
}

