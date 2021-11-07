package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Image image(@PathVariable("username") String username) {
        var image = new Image();
        image.setContent(imageGenerator.generate(username));
        return image;
    }

    @GetMapping(value = "/content/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public byte[] imageContent(@PathVariable("username") String username) {
        return imageGenerator.generate(username);
    }

}
