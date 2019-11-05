package me.skrib.messages.exception;

import io.damru.exception.model.ApiException;
import org.springframework.http.HttpStatus;

public class MessageTooFarException extends ApiException {

    public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.FORBIDDEN;
    public static final String DEFAULT_ERROR_CODE = "MessageTooFar";

    public MessageTooFarException() {
        super(DEFAULT_HTTP_STATUS, DEFAULT_ERROR_CODE);
    }

    public MessageTooFarException(String key, Object value) {
        this();
        this.addError(key, value);
    }

}
