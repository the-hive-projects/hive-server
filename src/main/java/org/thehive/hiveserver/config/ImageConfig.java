package org.thehive.hiveserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thehive.hiveserver.image.ImageGenerator;
import org.thehive.hiveserver.image.RandomImageGenerator;

@Configuration
public class ImageConfig {

    @Value("${image.generator.random.size}")
    public int randomGeneratorSize;

    @Value("${image.generator.random.minSquare}")
    public int randomGeneratorMinSquare;

    @Bean
    public ImageGenerator imageGenerator() {
        return new RandomImageGenerator(randomGeneratorSize, randomGeneratorMinSquare);
    }

}
