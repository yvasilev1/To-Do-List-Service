package org.example.todo.list.domain.error;

import lombok.Getter;

import java.io.Serial;

@Getter
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorResponse errorResponse;

    public NotFoundException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
