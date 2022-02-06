package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkContent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WinkContentImage implements WinkContent {
    private final BufferedImage image;

    public WinkContentImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public ByteArrayOutputStream toByteArray() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", stream);
        return stream;
    }
}
