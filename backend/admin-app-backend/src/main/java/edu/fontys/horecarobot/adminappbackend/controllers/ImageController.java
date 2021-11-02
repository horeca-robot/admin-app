package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4000")
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(path = "/{name}")
    public byte[] getImageByName(@PathVariable String name){

        byte[] imageBytes = imageService.getBase64FromDisk(name);

        return  imageBytes;
    }

}
