package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class StompChannelOutboundInterceptor implements ChannelInterceptor {

    private final Map<String, String> uniqueIdToTopic = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> topicToSessionCount = new ConcurrentHashMap<>();

    @Override
    public boolean preReceive(MessageChannel channel) {
        log.debug("Start preReceive()");

        return ChannelInterceptor.super.preReceive(channel);
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.debug("Start postReceive()");

        return ChannelInterceptor.super.postReceive(message, channel);
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        ChannelInterceptor.super.afterSendCompletion(message, channel, sent, ex);
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.debug("Start preSend()");

        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        final String destination = accessor.getDestination();
        final StompCommand command = accessor.getCommand();
        final var uniqueId = getUniqueSessionSubscriptionId(message);

        if (!accessor.isHeartbeat()) {
            handleCommand(command, destination, uniqueId);
        }

        return message;
    }

    private void handleCommand(StompCommand command, String destination, String uniqueId) {
        if (command == null) {
            log.debug("Command is null");
            return;
        }

        switch (Objects.requireNonNull(command)) {
            case CONNECT -> {
                log.info("CONNECT {}; uniqueId {}", destination, uniqueId);
                // Do something on CONNECT
            }
            case CONNECTED -> {
                log.debug("CONNECTED {}; uniqueId {}", destination, uniqueId);
                // Do something on CONNECTED
            }
            case ERROR -> {
                log.debug("ERROR {}; uniqueId {}", destination, uniqueId);
            }
            case STOMP -> {
                log.debug("STOMP {}; uniqueId {}", destination, uniqueId);
                // Do something on STOMP
            }
            case ACK -> {
                log.debug("ACK {}; uniqueId {}", destination, uniqueId);
                // Do something on ACK
            }
            case NACK -> {
                log.debug("NACK {}; uniqueId {}", destination, uniqueId);
                // Do something on NACK
            }
            case BEGIN -> {
                log.debug("BEGIN {}; uniqueId {}", destination, uniqueId);
                // Do something on BEGIN
            }
            case ABORT -> {
                log.debug("ABORT {}; uniqueId {}", destination, uniqueId);
                // Do something on ABORT
            }
            case MESSAGE -> {
                log.info("MESSAGE {}; uniqueId {}", destination, uniqueId);
                uniqueIdToTopic.put(uniqueId, destination);
            }
            case SUBSCRIBE -> {
                log.info("SUBSCRIBE {}; uniqueId {}", destination, uniqueId);
                uniqueIdToTopic.put(uniqueId, destination);
                if (Objects.nonNull(destination)) {
                    topicToSessionCount.putIfAbsent(destination, new AtomicLong(0));
                    topicToSessionCount.get(destination).incrementAndGet();
                }
            }
            case UNSUBSCRIBE -> {
                log.info("UNSUBSCRIBE; uniqueId {};", uniqueId);
                var topic = uniqueIdToTopic.get(uniqueId);
                log.info("Unsubscribed for uniqueId {} from topic {};", uniqueId, topic);
            }
            case DISCONNECT -> {
                log.info("DISCONNECT for uniqueId {}", uniqueId);
            }
        }
    }

    private String getUniqueSessionSubscriptionId(Message<?> message) {
        log.debug("Try to build UniqueId message: {}", message);
        SimpMessageHeaderAccessor wrap = SimpMessageHeaderAccessor.wrap(message);
        log.debug("UniqueId is session:subscription({}:{})", wrap.getSessionId(), wrap.getSubscriptionId());
        return String.format("%s:%s", wrap.getSessionId(), Objects.requireNonNullElse(wrap.getSubscriptionId(), ""));
    }

    private Optional<String> needCancel(String topic, String nameTopic) {
        var count = topicToSessionCount.get(topic).decrementAndGet();
        log.info("for topic {} count sessions {}", topic, count);
        return (count == 0) ? Optional.of(topic.replace(nameTopic, "")) : Optional.empty();
    }
}
