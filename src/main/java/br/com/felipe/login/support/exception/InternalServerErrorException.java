package br.com.felipe.login.support.exception;

import br.com.felipe.login.support.enums.ApplicationMessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends ApplicationException {

    private static final long serialVersionUID = 6294964023979538175L;

    public <T> InternalServerErrorException(ApplicationMessageCode code) {
        super(code);
    }
}
