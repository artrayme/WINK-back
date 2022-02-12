package analyser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AutocompleteTest {
    private static List<String> dataset;

    @BeforeAll
    static void loadDataset() throws IOException {
        var resource = AutocompleteTest.class.getResource("/analyser/all_idtfs.txt");
        dataset = Files.readAllLines(Path.of(resource.getPath()))
                .stream()
                .toList();
    }


    @Test
    public void testAutocompleteForAllIdtfs() {
        System.out.println(AutocompleteTest.dataset);
    }

    @Test
    public void testAutocompleteForNIdtfs() {

    }

}
