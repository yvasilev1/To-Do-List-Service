package org.example.todo.list.controller;

import jakarta.validation.Valid;
import org.example.todo.list.domain.ToDoItem;
import org.example.todo.list.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ToDoListController {
    public final ToDoListService toDoListService;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @PostMapping("/todolist/addToDoItem")
    public ToDoItem addToDoItem(@Valid @RequestBody ToDoItem toDoItem) {
        return toDoListService.addToDoItem(toDoItem);
    }

    @PutMapping("/todolist/updateById/{id}")
    public ResponseEntity<HttpStatus> updateToDoItemById(@PathVariable("id") UUID id, @Valid @RequestBody ToDoItem toDoItem) {
        int success = toDoListService.updateToDoItemById(id, toDoItem.getTitle(), toDoItem.getDescription(), toDoItem.getStatus());

        if (success == 1) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/todolist/getAllToDoItems")
    public List<ToDoItem> getAllToDoItems() {
        return toDoListService.getAllToDoItems();
    }

    @GetMapping("/todolist/getToDoItem/{id}")
    public Optional<ToDoItem> getToDoItemById(@PathVariable("id") UUID id) {
        return toDoListService.getToDoItemById(id);
    }

    @DeleteMapping("/todolist/deleteToDoItem/{id}")
    public ResponseEntity<HttpStatus> deleteToDoItem(@PathVariable("id") UUID id) {
        int deletedItems = toDoListService.deleteToDoItemById(id);

        if (deletedItems > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
