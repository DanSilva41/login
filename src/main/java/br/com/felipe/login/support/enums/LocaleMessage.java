package br.com.felipe.login.support.enums;

/**
 * @author Danilo Silva
 **/
public enum LocaleMessage {

    EN("en"),
    PT_BR("pt_BR");

    private final String language;

    /**
     * @param language : the language
     */
    LocaleMessage(final String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * @see Enum#toString()
     */
    @Override
    public String toString() {
        return language;
    }
}
