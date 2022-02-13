package org.wink.engine.analyser.autocompleter;

import java.util.List;

/**
 * @author Inejka
 * @since 0.0.1
 */
public interface Autocompleter {
    List<String> search(String part);

    List<String> search(String part, int limit);

}
