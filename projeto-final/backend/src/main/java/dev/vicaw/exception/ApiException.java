package dev.vicaw.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ApiException extends RuntimeException {

    private final int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

}