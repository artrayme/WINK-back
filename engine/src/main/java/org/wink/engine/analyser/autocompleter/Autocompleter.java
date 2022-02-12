package org.wink.engine.analyser.autocompleter;

/**
 * @author Inejka
 * @since 0.0.1
 */
public interface Autocompleter {
    Iterable<String> search(String part);

    Iterable<String> search(String part, int limit);

}
