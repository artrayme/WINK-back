package org.wink.engine.analyser.autocompleter.util;

import com.miguelfonseca.completely.IndexAdapter;
import com.miguelfonseca.completely.data.ScoredObject;
import com.miguelfonseca.completely.text.index.FuzzyIndex;
import com.miguelfonseca.completely.text.index.PatriciaTrie;
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton;
import org.wink.engine.analyser.autocompleter.util.SampleRecord;

import java.util.Collection;

public class SampleAdapter implements IndexAdapter<SampleRecord>
{
    private FuzzyIndex<SampleRecord> index = new PatriciaTrie<>();

    @Override
    public Collection<ScoredObject<SampleRecord>> get(String token)
    {
        // Set threshold according to the token length
        double threshold = Math.log(Math.max(token.length() - 1, 1));
        return index.getAny(new EditDistanceAutomaton(token, threshold));
    }

    @Override
    public boolean put(String token, SampleRecord value)
    {
        return index.put(token, value);
    }

    @Override
    public boolean remove(SampleRecord value)
    {
        return index.remove(value);
    }
}
