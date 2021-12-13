package org.thehive.hiveserver.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

@Slf4j
class NumericalSessionJoinIdGeneratorTest {

    static final int LENGHT = 11;

    NumericalSessionJoinIdGenerator idGenerator = new NumericalSessionJoinIdGenerator(LENGHT);

    @DisplayName("Generate ids and check byte range")
    @Test
    void generateIdsAndCheckByteRange() {
        final var repeat = 100;
        IntStream.range(0, repeat).parallel().forEach(i -> {
            var id = idGenerator.generate();
            log.info("Id: {}", id);
            for (var b : id.getBytes(StandardCharsets.UTF_8))
                Assertions.assertTrue(b >= 48 && b < 58);
        });

    }

}