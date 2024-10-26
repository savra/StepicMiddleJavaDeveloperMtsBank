package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.config.TestcontainersConfig.POSTGRES_CONTAINER;
import static org.testcontainers.shaded.com.google.common.net.HttpHeaders.CONTENT_TYPE;

@ActiveProfiles("test")
@Import({TestcontainersConfig.class})
@SpringBootTest(classes = Application.class)
public abstract class TestConfig {
    public static final WireMockServer WIRE_MOCK = new WireMockServer(options().port(8081));

    static {
        WIRE_MOCK.start();
    }

    @DynamicPropertySource
    static void registerContainersProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.flyway.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.flyway.user", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.flyway.password", POSTGRES_CONTAINER::getPassword);
    }

    protected static void stubGet(String url, String responseBodyFile) {
        WIRE_MOCK.stubFor(get(urlEqualTo(url))
                .willReturn(ok()
                        .withBodyFile(responseBodyFile)
                        .withHeader(CONTENT_TYPE, "application/json;charset=UTF-8")));
    }

    protected static void stubDelete(String url, String responseBodyFile) {
        WIRE_MOCK.stubFor(delete(urlEqualTo(url))
                .willReturn(ok()
                        .withBodyFile(responseBodyFile)
                        .withHeader(CONTENT_TYPE, "application/json;charset=UTF-8")));
    }

    protected static void stubPost(String url, String responseBodyFile) {
        WIRE_MOCK.stubFor(post(urlEqualTo(url))
                .willReturn(ok()
                        .withBodyFile(responseBodyFile)
                        .withHeader(CONTENT_TYPE, "application/json;charset=UTF-8")));
    }
}
