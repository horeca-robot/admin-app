package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.Data;

import java.util.*;

@Data
public class ApiResponse {
    private boolean success;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static ApiResponse Ok(Optional<String> message){
        ApiResponse response = new ApiResponse();
        response.success = true;
        response.message = message.get();
        return response;
    }

    public static ApiResponse Error(Optional<String> message){
        ApiResponse response = new ApiResponse();
        response.success = false;
        response.message = message.get();
        return response;
    }

    public ApiResponse AddData(String key, Object data){
        this.data.put(key, data);
        return this;
    }
}
