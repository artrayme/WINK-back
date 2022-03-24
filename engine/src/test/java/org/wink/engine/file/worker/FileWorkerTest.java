package org.wink.engine.file.worker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Inejka
 * @since 0.0.1
 */
public class FileWorkerTest {
    public static FileWorker tested;

    @BeforeAll
    public static void initFileWorker() throws FileNotFoundException {
        try {
            tested = new DefaultFileWorker("src/test/resources/file.worker/wink.conf");
        } catch (IOException e) {
            throw new FileNotFoundException("wink.conf not found");
        }
    }

    @Test
    public void testWriteAndRead() throws IOException {
        byte[] tested_bytes = "https://www.youtube.com/watch?v=dQw4w9WgXcQ".getBytes();
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        temp.write(tested_bytes);
        tested.write("read_file", temp);
        assertArrayEquals(tested_bytes, tested.read("read_file"));
    }

    @Test
    public void testGetFilesList() {
        assertEquals(4, calculateCorrectOutputs(tested.getFilesList(), Arrays.asList("t1.scs", "f2/t2.gwf",
                "f2/f3/t3.gwf", "f2/f3/t4.scs")));
    }

    private int calculateCorrectOutputs(Iterable<String> output, List<String> expected) {
        int ans = 0;
        for (String i : output) {
            if (expected.contains(i)) ans++;
        }
        return ans;
    }
}
