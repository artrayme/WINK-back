package org.wink.module.http.scg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wink.engine.analyser.autocompleter.Autocompleter;
import org.wink.engine.analyser.autocompleter.DefaultAutocompleter;

import java.util.ArrayList;


/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@Configuration
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {

    private final Environment environment;

    @Autowired
    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    // todo pass a real dictionary to autocompleter's constructor
    @Bean
    public Autocompleter autocompleter() {
        return new DefaultAutocompleter(new ArrayList<>());
    }
}
