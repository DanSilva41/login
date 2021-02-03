package br.com.felipe.login.support.exception;

import br.com.felipe.login.support.enums.ApplicationMessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ApplicationException {

    private static final long serialVersionUID = 3123238084971178989L;

    public <T> NotFoundException(ApplicationMessageCode code) {
        super(code);
    }
}
