package site.ani4h.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse<T> {
    private String message = null;
    private T data = null;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public ApiResponse( T data) {
        this.data = data;
    }


    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>( message, null);
    }

    public static ApiResponse<List<String>> error(String message, List<String> errors) {
        return new ApiResponse<>( message, errors);
    }

    // Getters & Setters
}

