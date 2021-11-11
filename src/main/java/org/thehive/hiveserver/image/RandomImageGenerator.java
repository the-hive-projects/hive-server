package org.thehive.hiveserver.image;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

@RequiredArgsConstructor
public class RandomImageGenerator implements ImageGenerator {

    private static final String FORMAT_NAME ="png";

    private final int size;
    private final int minSquare;

    @SneakyThrows
    @Override
    public byte[] generate(@NonNull String seedString) {
        var seed = seedStringToLong(seedString);
        var r = new Random(seed);
        final float hue = r.nextFloat();
        final float saturation = (r.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        final var color = Color.getHSBColor(hue, saturation, luminance);
        var bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
        var drawnSquareCount = 0;
        while (drawnSquareCount < minSquare)
            for (var i = 0; i < size / 2; i++)
                for (var j = 0; j < size; j++)
                    if (r.nextFloat() < .17f) {
                        bufferedImage.setRGB(i, j, color.getRGB());
                        drawnSquareCount++;
                    } else
                        bufferedImage.setRGB(i, j, 0xff000000);
        for (var i = size / 2; i < size; i++)
            for (var j = 0; j < size; j++)
                bufferedImage.setRGB(i, j, bufferedImage.getRGB(size - i - 1, j));
        var os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, FORMAT_NAME, os);
        return os.toByteArray();
    }

    private long seedStringToLong(@NonNull String seedStr) {
        var seed=0L;
        var i=0;
        for ( var b:seedStr.getBytes())
            seed+=((long)b)*(++i);
        return seed;
    }

}
