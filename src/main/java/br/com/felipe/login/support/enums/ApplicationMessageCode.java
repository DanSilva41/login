package br.com.felipe.login.support.enums;

/**
 * Enum with exception code / business messages
 *
 * @author Danilo Silva
 **/
public enum ApplicationMessageCode {
    INVALID_REQUEST("INVALID_REQUEST"),
    USERNAME_ALREADY_IN_USE("user.username.already_in_use"),
    EMAIL_ALREADY_IN_USE("person.email.already_in_use"),
    APPLICATION_NAME_ALREADY_IN_USE("application.name.already_in_use"),
    APPLICATION_NOT_FOUND("application.not_found"),
    USER_NOT_FOUND("user.not_found");

    private final String key;

    /**
     * @param key : key of message
     */
    ApplicationMessageCode(final String key) {
        this.key = key;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return key;
    }
}
