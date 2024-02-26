package org.example.todo.list.service;

import org.example.todo.list.domain.ToDoItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoListService {

    ToDoItem addToDoItem(ToDoItem toDoItem);

    List<ToDoItem> getAllToDoItems();

    Optional<ToDoItem> getToDoItemById(UUID id);

    int deleteToDoItemById(UUID id);

    int updateToDoItemById(UUID id, String title, String description, String status);
}
