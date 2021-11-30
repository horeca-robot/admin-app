package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class RestaurantDayModel {

    private String name;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public RestaurantDayModel(String name, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
}
