package com.fxorder.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final String code;
    private final String message;
    private final T returnObject;

    private ApiResponse(String code, String message, T returnObject) {
        this.code = code;
        this.message = message;
        this.returnObject = returnObject;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("OK", "SUCCESS", data);
    }

    public static ApiResponse<Void> ok() {
        return new ApiResponse<>("OK", "SUCCESS", null);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
