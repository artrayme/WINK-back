package org.wink.module.http.scg.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wink.engine.analyser.autocompleter.Autocompleter;
import org.wink.engine.scmemory.ScMemoryManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class ScElementControllerTest {

    @Autowired
    private ScElementController scElementController;

    @MockBean
    private Autocompleter autocompleter;

    @MockBean
    private ScMemoryManager scMemoryManager;

    @LocalServerPort
    private int port;

    @Test
    public void contextLoads() {
        assertThat(scElementController).isNotNull();
    }

    @Test
    public void testShouldReturnAutocompletedWords() {

    }
}
