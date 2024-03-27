package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto;

import java.time.OffsetDateTime;

public class ApiError {
    OffsetDateTime dateOccurred;
    private String message;

    public ApiError(String message) {
        this.message = message;
    }

    public ApiError(String message, OffsetDateTime dateOccurred) {
        this(message);
        this.dateOccurred = dateOccurred;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getDateOccurred() {
        return dateOccurred;
    }

    public void setDateOccurred(OffsetDateTime dateOccurred) {
        this.dateOccurred = dateOccurred;
    }
}