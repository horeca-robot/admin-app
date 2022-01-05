package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.request.TagRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.response.TagResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/tag")
@CrossOrigin()
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<?> getTags() {
        List<TagResponseModel> tags;

        try {
            tags = tagService.getAllTags();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("tags", tags));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTagById(@PathVariable UUID id) {
        Optional<TagResponseModel> tag;
        try {
            tag = tagService.getTagById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        if (tag.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Tag not found"));
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("tag", tag.get()));
    }

    @PostMapping
    public ResponseEntity<?> postTag(@RequestBody TagRequestModel tag) {
        if(tag.getName().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        TagResponseModel tagResponseModel;
        try {
            tagResponseModel = tagService.addTag(tag);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        var uri = URI.create("api/tag/" + tagResponseModel.getId());
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable UUID id) {
        try {
            tagService.deleteTag(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
