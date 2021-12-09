package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Tag;
import lombok.Data;

import java.util.UUID;

@Data
public class TagResponseModel {

    public TagResponseModel(Tag tag) {
        id = tag.getId();
        name = tag.getName();
    }

    private final UUID id;
    private final String name;
}
