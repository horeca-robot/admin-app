package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.*;

@Data
public class ApiResponse {

    public static final String REQUIRED_FIELDS_ERROR = "Not all required fields are filled in.";
    public static final String DATABASE_CONNECTION_ERROR = "Something went wrong, try again later.";
    public static final String ID_ALIGN_ERROR = "Id does not align with given object";

    private final boolean success;
    @Nullable
    private final String message;
    private Map<String, Object> data;

    private ApiResponse(boolean success, @Nullable String message) {
        this.success = success;
        this.message = message;
        data = new HashMap<>();
    }

    public static ApiResponse ok(){
        return ok(null);
    }

    public static ApiResponse ok(@Nullable String message){
        return new ApiResponse(true, message);
    }

    public static ApiResponse error(){
        return error(null);
    }

    public static ApiResponse error(@Nullable String message){
        return new ApiResponse(false, message);
    }

    public ApiResponse addData(String key, Object data){
        this.data.put(key, data);
        return this;
    }
}
