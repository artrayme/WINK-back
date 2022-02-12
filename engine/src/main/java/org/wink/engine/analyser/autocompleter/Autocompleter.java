package org.wink.engine.analyser.autocompleter;

import com.miguelfonseca.completely.AutocompleteEngine;
import org.wink.engine.analyser.autocompleter.util.SampleAdapter;
import org.wink.engine.analyser.autocompleter.util.SampleRecord;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Autocompleter {
    public Autocompleter(Iterable<String> dictionary) {
        engine = new AutocompleteEngine.Builder<SampleRecord>()
                .setIndex(new SampleAdapter())
                .setAnalyzers()
                .build();
        for (String i : dictionary)
            engine.add(new SampleRecord(i));
    }

    public Iterable<String> search(String part) {
        return engine.search(part).stream().map(Objects::toString).collect(Collectors.toList());
    }

    public Iterable<String> search(String part, int limit) {
        return engine.search(part, limit).stream().map(Objects::toString).collect(Collectors.toList());
    }

    private AutocompleteEngine<SampleRecord> engine;
}
