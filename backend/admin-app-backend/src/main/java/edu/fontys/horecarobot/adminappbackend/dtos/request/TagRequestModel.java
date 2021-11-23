package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class TagRequestModel {
    @NonNull
    private String name;
}
