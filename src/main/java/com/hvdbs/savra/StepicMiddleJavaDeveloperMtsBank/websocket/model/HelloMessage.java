package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.websocket.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }
}
