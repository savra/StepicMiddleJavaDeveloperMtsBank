package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.controller;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.model.Greeting;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.model.HelloMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        if (message.getName().equals("savra")) {
            return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        } else {
            throw new RuntimeException("test");
        }
    }

    @SendTo("/topic/greetings")
    @MessageExceptionHandler
    public WebSocketMessage<String> handleException(Message<HelloMessage> message, final Exception exception) {
        //log.error("Exception occurred", exception);

        WebSocketMessage<String> webSocketMessage = new TextMessage(exception.getMessage());
        return webSocketMessage;
    }
}
