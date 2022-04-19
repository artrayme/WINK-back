package org.wink.module.http.scg.config;

import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.ScMemory;
import org.ostis.scmemory.websocketmemory.memory.SyncOstisScMemory;
import org.ostis.scmemory.websocketmemory.util.api.IdtfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wink.engine.analyser.autocompleter.Autocompleter;
import org.wink.engine.analyser.autocompleter.DefaultAutocompleter;
import org.wink.engine.converter.rdf.RdfToWinkConverter;
import org.wink.engine.converter.rdf.impl.RdfToWinkConverterBasic;
import org.wink.engine.scmemory.OstisScMemoryManager;
import org.wink.engine.scmemory.ScMemoryManager;

import java.net.URI;


/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:ostis_config.properties")
public class ApplicationConfig implements WebMvcConfigurer {

    private static final String URI = "uri";
    private final Environment environment;

    @Autowired
    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ScMemory scMemory() throws Exception {
        URI uri = new URI(environment.getProperty(URI));
        ScMemory scMemory = new SyncOstisScMemory(uri);
        scMemory.open();
        return scMemory;
    }

    @Bean
    public Autocompleter autocompleter(ScMemory scMemory) throws Exception {
        SyncOstisScMemory syncOstisScMemory = (SyncOstisScMemory) scMemory;
        Iterable<String> identifiers = IdtfUtils.getAllIdtfFast(syncOstisScMemory);
        return new DefaultAutocompleter(identifiers);
    }

    @Bean
    public ScMemoryManager scMemoryManager(DefaultScContext defaultScContext) throws Exception {
        return new OstisScMemoryManager(defaultScContext);
    }

    @Bean
    public DefaultScContext defaultScContext(ScMemory scMemory) {
        return new DefaultScContext(scMemory);
    }

    @Bean
    public RdfToWinkConverter rdfToWinkConverter() {
        return new RdfToWinkConverterBasic();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
