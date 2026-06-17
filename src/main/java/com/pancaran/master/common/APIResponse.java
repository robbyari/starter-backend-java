package com.pancaran.master.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private Integer code;
    private Object message;
    private T data;

    public static <T> APIResponse<T> success(T data) {
        return APIResponse.<T>builder()
                .code(200)
                .message("Operation completed successfully")
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> success(T data, Object message) {
        return APIResponse.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> error(Integer code, Object message) {
        return APIResponse.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }
}
