package org.wink.module.http.scg.config;

import org.ostis.scmemory.model.ScMemory;
import org.ostis.scmemory.websocketmemory.memory.SyncOstisScMemory;
import org.ostis.scmemory.websocketmemory.util.api.IdtfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wink.engine.analyser.autocompleter.Autocompleter;
import org.wink.engine.analyser.autocompleter.DefaultAutocompleter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:ostis_config.properties")
public class ApplicationConfig implements WebMvcConfigurer {

    private final Environment environment;

    private static final String URI = "uri";

    @Autowired
    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ScMemory scMemory() throws URISyntaxException {
        URI uri = new URI(environment.getProperty(URI));
        return new SyncOstisScMemory(uri);
    }

    @Bean
    public Autocompleter autocompleter(ScMemory scMemory) throws Exception {
        SyncOstisScMemory syncOstisScMemory = (SyncOstisScMemory) scMemory;
        Iterable<String> identifiers = IdtfUtils.getAllIdtfFast(syncOstisScMemory);
        return new DefaultAutocompleter(identifiers);
    }
}
