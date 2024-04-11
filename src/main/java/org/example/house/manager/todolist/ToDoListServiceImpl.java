package org.example.house.manager.todolist;

import org.example.house.manager.error.ErrorResponse;
import org.example.house.manager.error.NotFoundException;
import org.example.house.manager.todolist.dao.ToDoListRepository;
import org.example.house.manager.todolist.domain.ToDoItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    public ToDoListRepository toDoListRepository;

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
        Optional<ToDoItem> toDoItem = toDoListRepository.findById(id);

        if (toDoItem.isPresent()) {
            return toDoItem;
        }

        throw new NotFoundException(new ErrorResponse(404, "No TODO item found with provided Id"));
    }

    @Override
    public int deleteToDoItemById(UUID id) {
        return toDoListRepository.deleteToDoItemById(id);
    }

    @Override
    public int updateToDoItemById(UUID id, String title, String description, String status) {
        return toDoListRepository.updateById(title, description, status, id);
    }
}
