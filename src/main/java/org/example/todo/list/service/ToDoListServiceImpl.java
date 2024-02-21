package org.example.todo.list.service;

import org.example.todo.list.dao.ToDoListRepository;
import org.example.todo.list.domain.ItemStatus;
import org.example.todo.list.domain.ToDoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    public ToDoListRepository toDoListRepository;

    @Autowired
    public ToDoListServiceImpl(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    @Override
    public ToDoItem addToDoItem(ToDoItem toDoItem) {
        return toDoListRepository.save(toDoItem);
    }

    @Override
    public List<ToDoItem> getAllToDoItems() {
        Iterable<ToDoItem> iterable = toDoListRepository.findAll();

        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<ToDoItem> getToDoItemById(UUID id) {
        return toDoListRepository.findById(id);
    }

    @Override
    public int deleteToDoItemById(UUID id) {
       return toDoListRepository.deleteToDoItemById(id);
    }

    @Override
    public int updateToDoItemById(UUID id, String title, String description, ItemStatus status) {
        return toDoListRepository.updateById(title, description, status, id);
    }
}
