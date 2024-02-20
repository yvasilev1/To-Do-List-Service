package org.example.todo.list.domain;

import lombok.Getter;

@Getter
public enum ItemStatus {
    PENDING("PENDING"), COMPLETED("COMPLETED"), IN_PROGRESS("INPROGRESS");

    private final String status;

    ItemStatus(String status) {
        this.status = status;
    }

}
