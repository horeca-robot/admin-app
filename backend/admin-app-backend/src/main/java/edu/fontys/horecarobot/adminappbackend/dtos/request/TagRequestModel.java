package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class TagRequestModel {
    @NonNull
    private String name;
}
