package edu.fontys.horecarobot.adminappbackend.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/restaurant")
public class RestaurantController {

    @PostMapping("/upload/logo")
    public String UploadLogo(@RequestParam("image") MultipartFile multipartFile)
    {
        if(multipartFile.isEmpty()) return "File is empty";

        return String.format("File %s has been uploaded", multipartFile.getName());
    }

    @GetMapping(path = "/test")
    public String PleaseWork()
    {
        return  "I Work";
    }
}
