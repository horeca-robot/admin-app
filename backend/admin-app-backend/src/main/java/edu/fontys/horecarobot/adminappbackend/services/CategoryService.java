package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService{
    public List<Category> getCategories() {
        return List.of(
                new Category(
                        "Soep", "Foto", "Voorgerechten"
                )
        );
    }
}
