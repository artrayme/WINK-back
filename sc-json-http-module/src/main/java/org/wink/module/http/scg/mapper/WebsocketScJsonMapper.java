package org.wink.module.http.scg.mapper;

import org.ostis.scmemory.websocketmemory.memory.pattern.DefaultWebsocketScPattern;
import org.springframework.stereotype.Component;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@Component
public class WebsocketScJsonMapper {

    public DefaultWebsocketScPattern map() {

        return new DefaultWebsocketScPattern();
    }
}
