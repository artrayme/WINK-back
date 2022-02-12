package org.wink.engine.analyser.autocompleter;

import com.miguelfonseca.completely.AutocompleteEngine;
import org.wink.engine.analyser.autocompleter.util.SampleAdapter;
import org.wink.engine.analyser.autocompleter.util.SampleRecord;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Inejka
 * @since 0.0.1
 */
public class DefaultAutocompleter implements Autocompleter {
    private AutocompleteEngine<SampleRecord> engine;

    public DefaultAutocompleter(Iterable<String> dictionary) {
        engine = new AutocompleteEngine.Builder<SampleRecord>()
                .setIndex(new SampleAdapter())
                .setAnalyzers()
                .build();
        for (String i : dictionary)
            engine.add(new SampleRecord(i));
    }

    @Override
    public Iterable<String> search(String part) {
        return engine.search(part).stream().map(Objects::toString).collect(Collectors.toList());
    }

    @Override
    public Iterable<String> search(String part, int limit) {
        return engine.search(part, limit).stream().map(Objects::toString).collect(Collectors.toList());
    }
}
