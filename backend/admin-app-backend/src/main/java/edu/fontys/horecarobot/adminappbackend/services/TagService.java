package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.TagRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.TagResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.Tag;
import edu.fontys.horecarobot.databaselibrary.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponseModel> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagResponseModel::new)
                .collect(Collectors.toList());
    }

    public Optional<TagResponseModel> getTagById(UUID id) {
        return tagRepository.findById(id).map(TagResponseModel::new);
    }

    public Optional<TagResponseModel> getTagByName(String name) {
        Tag tag = new Tag();
        tag.setName(name);

        Example<Tag> example = Example.of(tag);
        return tagRepository.findOne(example).map(TagResponseModel::new);
    }

    public TagResponseModel addTag(TagRequestModel tagRequestModel) {
        var tag = new Tag(null, tagRequestModel.getName());
        tag = tagRepository.save(tag);
        return new TagResponseModel(tag);
    }

    public void deleteTag(UUID id) {
        tagRepository.deleteById(id);
    }

}
