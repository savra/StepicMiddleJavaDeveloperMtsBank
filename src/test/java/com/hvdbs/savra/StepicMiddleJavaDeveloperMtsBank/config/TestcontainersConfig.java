package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Testcontainers(disabledWithoutDocker = true)
public class TestcontainersConfig {
    public static final KafkaContainer KAFKA_CONTAINER;
    public static final GenericContainer<?> REDIS_CONTAINER;
    public static final PostgreSQLContainer<?> POSTGRES_CONTAINER;
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

        POSTGRES_CONTAINER = new PostgreSQLContainer<>(postgresImage)
                .withDatabaseName(TEST_DATABASE_NAME)
                .withUsername(TEST_USER)
                .withPassword(TEST_PASSWORD)
                .withNetwork(NETWORK)
                .withUrlParam("characterEncoding", "UTF-8");

        POSTGRES_CONTAINER.start();

        KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse(properties.getProperty("kafka.container.image"))
                .asCompatibleSubstituteFor("confluentinc/cp-kafka"));
        KAFKA_CONTAINER.start();

        REDIS_CONTAINER = new GenericContainer<>(DockerImageName.parse(properties.getProperty("redis.container.image"))
                .asCompatibleSubstituteFor("redis"))
                .withExposedPorts(6379);
        REDIS_CONTAINER.start();
    }

    @BeforeAll
    static void checkContainersRunning() {
        assertTrue(POSTGRES_CONTAINER.isRunning());
        assertTrue(KAFKA_CONTAINER.isRunning());
        assertTrue(REDIS_CONTAINER.isRunning());
    }

    @AfterAll
    static void closeContainers() {
        POSTGRES_CONTAINER.close();
        KAFKA_CONTAINER.close();
        REDIS_CONTAINER.close();
    }
}
