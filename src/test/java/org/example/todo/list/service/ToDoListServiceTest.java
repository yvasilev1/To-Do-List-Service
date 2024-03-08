package org.example.todo.list.service;

import org.example.todo.list.dao.ToDoListRepository;
import org.example.todo.list.domain.ToDoItem;
import org.example.todo.list.domain.error.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoListServiceTest {

    @Mock
    private ToDoListRepository toDoListRepository;
    private ToDoListService toDoListService;
    private UUID id;

    @BeforeEach
    void setUp() {
        toDoListService = new ToDoListServiceImpl(toDoListRepository);
        id = UUID.randomUUID();
    }

    @AfterEach
    void tearDown() {
        toDoListService = null;
    }

    @Test
    public void addToDoItem_success() {
        ToDoItem expectedToDoItem = createItem(id, "PENDING", "someTitle", "someDescription");

        when(toDoListRepository.save(any(ToDoItem.class))).thenReturn(expectedToDoItem);

        ToDoItem actualTodoItem = toDoListService.addToDoItem(expectedToDoItem);

        assertResponseIsCorrect(actualTodoItem, expectedToDoItem);
    }

    @Test
    public void getAllToDoItems_noToDoItemsReturned() {

        when(toDoListRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<ToDoItem> toDoItems = toDoListService.getAllToDoItems();

        assertEquals(0, toDoItems.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void getAllToDoItems_success() {

        when(toDoListRepository.findAll()).thenReturn(List.of(createItem(id, "PENDING", "someTitle", "someDescription")));

        Iterable<ToDoItem> toDoItems = toDoListService.getAllToDoItems();

        assertNotNull(toDoItems);
        assertEquals(1, toDoItems.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void getToDoItemById_noToDoItemFound() {

        when(toDoListRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> toDoListService.getToDoItemById(id));
    }

    @Test
    public void getToDoItemById_success() {

        ToDoItem expectedToDoItem = createItem(id, "PENDING", "someTitle", "someDescription");

        when(toDoListRepository.findById(any(UUID.class))).thenReturn(Optional.of(expectedToDoItem));

        Optional<ToDoItem> actualTodoItem = toDoListService.getToDoItemById(id);

        assertResponseIsCorrect(actualTodoItem.get(), expectedToDoItem);
    }


    @Test
    public void deleteToDoItemById_noToDoItemDeleted() {

        when(toDoListRepository.deleteToDoItemById(any(UUID.class))).thenReturn(0);

        int response = toDoListService.deleteToDoItemById(id);

        assertEquals(0, response);
    }

    @Test
    public void deleteToDoItemById_success() {

        when(toDoListRepository.deleteToDoItemById(any(UUID.class))).thenReturn(1);

        int response = toDoListService.deleteToDoItemById(id);

        assertEquals(1, response);
    }

    @Test
    public void updateToDoItemById_noToDoItemUpdated() {

        when(toDoListRepository.updateById(anyString(), anyString(), anyString(), any(UUID.class))).thenReturn(0);

        int response = toDoListService.updateToDoItemById(id, "some title", "some description", "PENDING");

        assertEquals(0, response);
    }

    @Test
    public void updateToDoItemById_success() {

        when(toDoListRepository.updateById(anyString(), anyString(), anyString(), any(UUID.class))).thenReturn(1);

        int response = toDoListService.updateToDoItemById(id, "some title", "some description", "PENDING");

        assertEquals(1, response);
    }

    public static ToDoItem createItem(UUID id, String status, String title, String description) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId(id);
        toDoItem.setStatus(status);
        toDoItem.setTitle(title);
        toDoItem.setDescription(description);
        return toDoItem;
    }

    private static void assertResponseIsCorrect(ToDoItem actualTodoItem, ToDoItem expectedToDoItem) {
        assertNotNull(actualTodoItem);
        assertEquals(expectedToDoItem.getId(), actualTodoItem.getId());
        assertEquals(expectedToDoItem.getStatus(), actualTodoItem.getStatus());
        assertEquals(expectedToDoItem.getTitle(), actualTodoItem.getTitle());
        assertEquals(expectedToDoItem.getDescription(), actualTodoItem.getDescription());
    }
}