package org.example.todo.list.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ItemStatus {
    PENDING("PENDING"), COMPLETED("COMPLETED"), IN_PROGRESS("INPROGRESS");

    private final String status;

    ItemStatus(String status) {
        this.status = status;
    }

   public static boolean containsStatus(ItemStatus itemStatus){
        return Arrays.stream(ItemStatus.values()).anyMatch(status1 -> status1.status.equalsIgnoreCase(itemStatus.getStatus()));
    }

}
