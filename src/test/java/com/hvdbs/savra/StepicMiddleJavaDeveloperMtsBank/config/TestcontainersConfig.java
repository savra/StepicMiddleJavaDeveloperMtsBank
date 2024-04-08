package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Testcontainers(disabledWithoutDocker = true)
public class TestcontainersConfig {
    public static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
    private static final String TEST_DATABASE_NAME = "testdb";
    private static final String TEST_USER = "postgres";
    private static final String TEST_PASSWORD = "postgres";
    private static final Network NETWORK = Network.newNetwork();
    public static final Properties properties = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream propertiesStream = classLoader.getResourceAsStream("testcontainers.properties")) {
            properties.load(propertiesStream);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        DockerImageName postgresImage = DockerImageName.parse(properties.getProperty("postgres.container.image"))
                .asCompatibleSubstituteFor("postgres");

        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(postgresImage)
                .withDatabaseName(TEST_DATABASE_NAME)
                .withUsername(TEST_USER)
                .withPassword(TEST_PASSWORD)
                .withNetwork(NETWORK)
                .withUrlParam("characterEncoding", "UTF-8");

        POSTGRE_SQL_CONTAINER.start();
    }

    @BeforeAll
    static void checkContainersRunning() {
        assertTrue(POSTGRE_SQL_CONTAINER.isRunning());
    }

    @AfterAll
    static void closeContainers() {
        POSTGRE_SQL_CONTAINER.close();
    }
}
