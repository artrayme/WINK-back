package org.wink.engine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Inejka
 * @since 0.0.1
 */
public class ConfigTest {
    @BeforeAll
    public static void init() throws IOException {
        Config.init("src/test/resources/wink.conf");
        Config.init("src/test/resources/wink.conf");
    }

    @Test
    public void propTest() {
        assertEquals("src/test/resources/file.worker/f1", Config.getProperty("kb.path"));
        assertEquals("https://www.youtube.com/watch?v=nmPPCkF6-fk", Config.getProperty("link.best"));
        assertEquals("2", Config.getProperty("my.iq"));
    }


}
