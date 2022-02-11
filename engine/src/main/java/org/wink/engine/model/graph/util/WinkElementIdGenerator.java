package org.wink.engine.model.graph.util;

import java.util.concurrent.atomic.AtomicLong;

public class WinkElementIdGenerator {
    private final static AtomicLong id = new AtomicLong(0);

    private WinkElementIdGenerator() {
    }

    public static Long getNextId() {
        return id.getAndIncrement();
    }
}
