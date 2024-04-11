package org.example.house.manager.error;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ErrorResponse {

    private final ZonedDateTime timestamp;
    private final int statusCode;
    private final String message;

    public ErrorResponse(int statusCode, String message) {
        this.timestamp = ZonedDateTime.now();
        this.statusCode = statusCode;
        this.message = message;
    }
}
