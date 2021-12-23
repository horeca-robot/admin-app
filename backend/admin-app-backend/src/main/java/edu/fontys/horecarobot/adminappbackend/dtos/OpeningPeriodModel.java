package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.OpeningPeriod;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class OpeningPeriodModel {

    private int dayOfWeek;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public OpeningPeriodModel(OpeningPeriod model) {
        dayOfWeek = model.getDayOfWeek();
        openingTime = model.getOpeningTime();
        closingTime = model.getClosingTime();
    }

}
