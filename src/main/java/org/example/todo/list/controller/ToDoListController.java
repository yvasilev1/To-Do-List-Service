package org.example.todo.list.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.todo.list.domain.ToDoItem;
import org.example.todo.list.service.ToDoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/todolist")
public class ToDoListController {
    public final ToDoListService toDoListService;

    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @PostMapping(value = "/addToDoItem", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ToDoItem addToDoItem(@Valid @RequestBody ToDoItem toDoItem) {
        return toDoListService.addToDoItem(toDoItem);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<HttpStatus> updateToDoItemById(@NotNull(message = "Id cannot be null") @PathVariable("id") UUID id, @Valid @RequestBody ToDoItem toDoItem) {
        int success = toDoListService.updateToDoItemById(id, toDoItem.getTitle(), toDoItem.getDescription(), toDoItem.getStatus());

        if (success == 1) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getAllToDoItems", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ToDoItem> getAllToDoItems() {
        return toDoListService.getAllToDoItems();
    }

    @GetMapping(value = "/getToDoItem/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<ToDoItem> getToDoItemById(@PathVariable("id") UUID id) {
        return toDoListService.getToDoItemById(id);
    }

    @DeleteMapping(value = "/deleteToDoItem/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteToDoItem(@PathVariable("id") UUID id) {
        int deletedItems = toDoListService.deleteToDoItemById(id);

        if (deletedItems > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
