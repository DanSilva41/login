package br.com.felipe.login.support.exception;

import br.com.felipe.login.support.enums.ApplicationMessageCode;


public class BadRequestException extends ApplicationException {

    private static final long serialVersionUID = 3123238084971178989L;

    public <T> BadRequestException(ApplicationMessageCode code) {
        super(code);
    }
}
