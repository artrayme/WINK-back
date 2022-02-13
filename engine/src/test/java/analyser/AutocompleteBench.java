package analyser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.wink.engine.analyser.autocompleter.DefaultAutocompleter;
import util.TimeExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author artrayme
 * @since 0.0.1
 */
@ExtendWith(TimeExtension.class)

public class AutocompleteBench {
    private static List<String> dataset;
    private static DefaultAutocompleter tested;

    @BeforeAll
    static void loadDataset() throws IOException {
        var resource = AutocompleteBench.class.getResource("/analyser/13kbench.txt");
        dataset = Files.readAllLines(Path.of(resource.getPath()))
                .stream()
                .toList();
        tested = new DefaultAutocompleter(dataset);
    }


    @Test
    public void testAutocompleteForAllIdtfs() {
        System.out.println(tested.search("действие").size());
    }

}
