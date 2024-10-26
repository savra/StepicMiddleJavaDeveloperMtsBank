package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.controller;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.config.TestConfig;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.model.Greeting;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.model.HelloMessage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.util.HtmlUtils;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingControllerTest extends TestConfig {
    private String WEBSOCKET_URL;
    private static final String WEBSOCKET_TOPIC = "/topic/greetings";
    private CompletableFuture<Greeting> completableFuture;

    private WebSocketStompClient webSocketStompClient;
    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setup() {
        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        this.webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        WEBSOCKET_URL = "ws://localhost:" + port + "/gs-guide-websocket";
        completableFuture = new CompletableFuture<>();
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    @Test
    void verifyGreetingIsReceived() throws Exception {
        // BlockingQueue<Greeting> blockingQueue = new ArrayBlockingQueue<>(1);

        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = webSocketStompClient
                .connect(WEBSOCKET_URL, new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/greetings", new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Greeting.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((Greeting) payload);
                //   blockingQueue.add((Greeting) payload);
            }
        });

        session.send("/app/hello", new HelloMessage("Sergey"));
        Greeting greeting = completableFuture.get(5, TimeUnit.SECONDS);
        MatcherAssert.assertThat(greeting.getContent(), Matchers.is("Hello, Sergey!"));

        // Awaitility.await()
        //   .atMost(5, TimeUnit.SECONDS)
        //.until(() -> Awaitility.fieldIn(blockingQueue.poll()).ofType(String.class).andWithName("content").equals("Hello, Sergey!"));
        //.untilAsserted(() -> assertEquals("Hello, Sergey!", blockingQueue.poll() != null));
    }

    @Test
    void testWebsocketReconnect() throws Exception {
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = webSocketStompClient
                .connect(WEBSOCKET_URL, new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/greetings", new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Greeting.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((Greeting) payload);
                //   blockingQueue.add((Greeting) payload);
            }
        });


        /*
        * 1. Подключаемся к сокету
        * 2. По ресту запрашиваем все события
        * 3. с таймаутом 1 сек из кафки идут сообщения в клиент, проверяем, что их 10 и их содержание
        * 4. дальше сервер останавливается
        * */
    }
}