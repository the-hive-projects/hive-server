package org.thehive.hiveserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thehive.hiveserver.image.ImageGenerator;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageApi {

    private final ImageGenerator imageGenerator;

    @GetMapping(value = "/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] profileImage(@PathVariable("username") String username) {
        return imageGenerator.generate(username);
    }

}
