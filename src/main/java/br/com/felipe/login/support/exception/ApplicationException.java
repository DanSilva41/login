package br.com.felipe.login.support.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class responsible for handling business executions of system.
 *
 * @author Danilo Silva
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -8251538872379467963L;

    public ApplicationException(String message) {
        super(message);
    }

    public <T> ApplicationException(T code) {
        super(getMessage(code));
    }

    public <T> ApplicationException(T code, String... parameters) {
        super(getFormattedMessage(code, parameters));
    }

    /**
     * Returns the message a formatted through a list of parameters.
     */
    private static <T> String getFormattedMessage(T code, String... parameters) {
        final String msg = getMessage(code);
        final List<String> formattedParameters = new ArrayList<>();
        if (parameters != null) {
            for (String param : parameters) {
                if (param != null)
                    formattedParameters.add(param);
            }
        }

        return MessageFormat.format(msg, formattedParameters);
    }

    public ApplicationException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Returns the message associated with the given key
     *
     * @param key corresponding message
     */
    private static <T> String getMessage(T key) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", LocaleContextHolder.getLocale());
        return bundle.getString(key.toString());
    }

}
