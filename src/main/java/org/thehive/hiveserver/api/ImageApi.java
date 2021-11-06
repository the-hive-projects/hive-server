package org.thehive.hiveserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thehive.hiveserver.entity.Image;
import org.thehive.hiveserver.image.ImageGenerator;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageApi {

    private final ImageGenerator imageGenerator;

    @GetMapping(value = "/{username}")
    public Image image(@PathVariable("username") String username) {
        var image = new Image();
        image.setContent(imageGenerator.generate(username));
        return image;
    }

    @GetMapping(value = "/content/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] imageContent(@PathVariable("username") String username) {
        return imageGenerator.generate(username);
    }

}
