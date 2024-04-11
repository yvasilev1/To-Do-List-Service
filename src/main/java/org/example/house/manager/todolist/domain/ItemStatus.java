package org.example.house.manager.todolist.domain;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public enum ItemStatus {

    PENDING("PENDING"), COMPLETED("COMPLETED"), IN_PROGRESS("INPROGRESS");

    private final String status;

    ItemStatus(String status) {
        this.status = status;
    }
}
