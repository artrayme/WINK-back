package org.wink.engine.analyser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.wink.engine.analyser.autocompleter.DefaultAutocompleter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * @author Inejka
 * @since 0.0.1
 */
public class AutocompleteTest {
    private static List<String> dataset;
    private static DefaultAutocompleter tested;

    @BeforeAll
    static void loadDataset() throws IOException {
        var resource = AutocompleteTest.class.getResource("/analyser/all_idtfs.txt");
        dataset = Files.readAllLines(Path.of(resource.getPath()))
                .stream()
                .toList();
        tested = new DefaultAutocompleter(dataset);
    }


    @Test
    public void testAutocompleteForAllIdtfs() {
        assertEquals(21, calculateCorrectOutputs(tested.search(""), Arrays.asList("Алжир",
                "Аруба",
                "Ангола",
                "Андорра Ботсвана",
                "Албания",
                "Армения",
                "Австрия",
                "Австралия",
                "Азербайджан Бельгия",
                "Аландские о-ва",
                "Антигуа и Барбуда",
                "Американское Самоа",
                "Бутан",
                "Багамы Ангилья",
                "Бурунди",
                "Бахрейн",
                "Боливия",
                "Ботсвана",
                "Беларусь",
                "Барбадос",
                "Британская территория в Индийском океане")));
        assertEquals(3, calculateCorrectOutputs(tested.search("Ан"), Arrays.asList("Ангола", "Антигуа и Барбуда", "Андорра Ботсвана")));
        assertEquals(1, calculateCorrectOutputs(tested.search("Бот"), Arrays.asList("Ботсвана")));
        assertEquals(0, calculateCorrectOutputs(tested.search("б"), Arrays.asList("Алжир",
                "Аруба",
                "Ангола",
                "Андорра Ботсвана",
                "Албания",
                "Армения",
                "Австрия",
                "Австралия",
                "Азербайджан Бельгия",
                "Аландские о-ва",
                "Антигуа и Барбуда",
                "Американское Самоа",
                "Бутан",
                "Багамы Ангилья",
                "Бурунди",
                "Бахрейн",
                "Боливия",
                "Ботсвана",
                "Беларусь",
                "Барбадос",
                "Британская территория в Индийском океане")));
    }

    @Test
    public void testAutocompleteForNIdtfs() {
        assertEquals(10, calculateCorrectOutputs(tested.search("",10), Arrays.asList("Алжир",
                "Аруба",
                "Ангола",
                "Андорра Ботсвана",
                "Албания",
                "Армения",
                "Австрия",
                "Австралия",
                "Азербайджан Бельгия",
                "Аландские о-ва",
                "Антигуа и Барбуда",
                "Американское Самоа",
                "Бутан",
                "Багамы Ангилья",
                "Бурунди",
                "Бахрейн",
                "Боливия",
                "Ботсвана",
                "Беларусь",
                "Барбадос",
                "Британская территория в Индийском океане")));
    }

    private int calculateCorrectOutputs(Iterable<String> output, List<String> expected) {
        int ans = 0;
        for (String i : output) {
            if (expected.contains(i)) ans++;
        }
        return ans;
    }

}
