package br.com.felipe.login.web.rest.handler;

import br.com.felipe.login.support.enums.ApplicationMessageCode;
import br.com.felipe.login.support.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {

        String userMessage = messageSource.getMessage(ApplicationMessageCode.INVALID_REQUEST.toString(), null, LocaleContextHolder.getLocale());
        String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<ApiError> apiErrors = Collections.singletonList(new ApiError(userMessage, developerMessage));
        return handleExceptionInternal(ex, apiErrors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {

        List<ApiError> errors = createListErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String userMessage = messageSource.getMessage(ApplicationMessageCode.INVALID_REQUEST.toString(), null,
            LocaleContextHolder.getLocale());
        String developerMessage = ex.toString();
        List<ApiError> apiErrors = Collections.singletonList(new ApiError(userMessage, developerMessage));

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

        String userMessage = messageSource.getMessage(ApplicationMessageCode.INVALID_REQUEST.toString(), null,
            LocaleContextHolder.getLocale());
        String developerMessage = ex.toString();
        List<ApiError> apiErrors = Collections.singletonList(new ApiError(userMessage, developerMessage));

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest request) {
        log.info("Throw exception:\n {0}", ex);
        String userMessage = messageSource.getMessage(ApplicationMessageCode.INVALID_REQUEST.toString(), null,
            LocaleContextHolder.getLocale());
        String developerMessage = ExceptionUtils.getRootCauseMessage(ex);
        List<ApiError> apiErrors = Collections.singletonList(new ApiError(userMessage, developerMessage));
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex,
                                                            WebRequest request) {
        log.info("Throw exception:\n {0}", ex);
        String userMessage = ex.getMessage();
        String developerMessage = ExceptionUtils.getRootCauseMessage(ex);
        List<ApiError> apiErrors = Collections.singletonList(new ApiError(userMessage, developerMessage));
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<ApiError> createListErrors(BindingResult bindingResult) {
        List<ApiError> apiErrors = new ArrayList<>(0);

        bindingResult.getFieldErrors().forEach(fieldError -> {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            apiErrors.add(new ApiError(userMessage, developerMessage));
        });

        return apiErrors;
    }

    @Getter
    public static class ApiError {

        private final String userMessage;
        private final String developerMessage;

        ApiError(String userMessage, String mensagemDesenvolvimento) {
            this.userMessage = userMessage;
            this.developerMessage = mensagemDesenvolvimento;
        }

    }

}

