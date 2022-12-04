package dev.vicaw.exception;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {

    private final int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

}