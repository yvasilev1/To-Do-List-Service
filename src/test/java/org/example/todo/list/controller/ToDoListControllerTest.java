package org.example.todo.list.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todo.list.domain.ToDoItem;
import org.example.todo.list.domain.error.ErrorResponse;
import org.example.todo.list.domain.error.NotFoundException;
import org.example.todo.list.service.ToDoListService;
import org.example.todo.list.service.ToDoListServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ToDoListController.class)
@ExtendWith(MockitoExtension.class)
public class ToDoListControllerTest {

    @MockBean
    private ToDoListService toDoListService;

    @Autowired
    private MockMvc mockMvc;

    private UUID id;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void addToDoItem_methodTypeNotAllowed() throws Exception {

        mockMvc.perform(get("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Request method 'GET' is not supported"));
    }

    @Test
    public void addToDoItem_emptyRequestBody() throws Exception {

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addToDoItem_invalidRequestBody() throws Exception {

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"SomeTitle\",\"description\": \"SomeDescription\"\"status\": \"PENDING\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("JSON parse error: Unexpected character ('\"' (code 34)): was expecting comma to separate Object entries"));
    }

    @Test
    public void addToDoItem_titleIsMissing() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", null, "SomeDescription");

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title is mandatory"));
    }

    @Test
    public void addToDoItem_titleIsEmpty() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "", "SomeDescription");

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title is mandatory"));
    }

    @Test
    public void addToDoItem_descriptionIsMissing() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", null);

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Description is mandatory"));
    }

    @Test
    public void addToDoItem_descriptionIsEmpty() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", "");

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Description is mandatory"));
    }

    @Test
    public void addToDoItem_statusMissing() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, null, "SomeTitle", "SomeDescription");

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("itemStatus not valid can be - PENDING, COMPLETED, IN_PROGRESS"));
    }

    @Test
    public void addToDoItem_statusIsEmpty() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "", "SomeTitle", "SomeDescription");

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("itemStatus not valid can be - PENDING, COMPLETED, IN_PROGRESS"));
    }

    @Test
    public void addToDoItem_statusIsInvalid() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "BLAH", "SomeTitle", "SomeDescription");

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("itemStatus not valid can be - PENDING, COMPLETED, IN_PROGRESS"));
    }

    @Test
    public void addToDoItem_success() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", "SomeDescription");

        when(toDoListService.addToDoItem(any(ToDoItem.class))).thenReturn(toDoItem);

        mockMvc.perform(post("/todolist/addToDoItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value(toDoItem.getTitle()))
                .andExpect(jsonPath("$.description").value(toDoItem.getDescription()))
                .andExpect(jsonPath("$.status").value(toDoItem.getStatus()));
    }

    @Test
    public void getAllToDoItems_methodTypeNotAllowed() throws Exception {

        mockMvc.perform(post("/todolist/getAllToDoItems"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Request method 'POST' is not supported"));
    }

    @Test
    public void getAllToDoItems_noToDoItemsReturned() throws Exception {

        when(toDoListService.getAllToDoItems()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/todolist/getAllToDoItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllToDoItems_success() throws Exception {

        UUID secondToDoItemId = UUID.randomUUID();

        List<ToDoItem> toDoItems = List.of(ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", "SomeDescription"), ToDoListServiceTest.createItem(secondToDoItemId, "PENDING", "SomeTitle", "SomeDescription"));

        when(toDoListService.getAllToDoItems()).thenReturn(toDoItems);

        mockMvc.perform(get("/todolist/getAllToDoItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[1].id").value(secondToDoItemId.toString()));
    }

    @Test
    public void getToDoItemById_methodTypeNotAllowed() throws Exception {
        mockMvc.perform(post("/todolist/getToDoItem/{id}", id))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Request method 'POST' is not supported"));
    }

    @Test
    public void getToDoItemById_invalidIdTypeProvided() throws Exception {
        mockMvc.perform(get("/todolist/getToDoItem/{id}", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid UUID string: invalid"));
    }

    @Test
    public void getToDoItemById_noDoItemWithProvidedId() throws Exception {

        when(toDoListService.getToDoItemById(any(UUID.class))).thenThrow(new NotFoundException(new ErrorResponse(404, "No TODO item found with provided Id")));

        mockMvc.perform(get("/todolist/getToDoItem/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No TODO item found with provided Id"));
    }

    @Test
    public void getToDoItemById_success() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", "SomeDescription");

        when(toDoListService.getToDoItemById(any(UUID.class))).thenReturn(Optional.of(toDoItem));

        mockMvc.perform(get("/todolist/getToDoItem/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value(toDoItem.getTitle()))
                .andExpect(jsonPath("$.description").value(toDoItem.getDescription()))
                .andExpect(jsonPath("$.status").value(toDoItem.getStatus()));
    }

    @Test
    public void deleteToDoItemById_methodTypeNotAllowed() throws Exception {
        mockMvc.perform(get("/todolist/deleteToDoItem/{id}", id))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Request method 'GET' is not supported"));
    }

    @Test
    public void deleteToDoItemById_invalidIdTypeProvided() throws Exception {
        mockMvc.perform(delete("/todolist/deleteToDoItem/{id}", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid UUID string: invalid"));
    }

    @Test
    public void deleteToDoItemById_noDoItemWithProvidedId() throws Exception {

        when(toDoListService.deleteToDoItemById(any(UUID.class))).thenReturn(0);

        mockMvc.perform(delete("/todolist/deleteToDoItem/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No TODO item found with provided Id"));
    }

    @Test
    public void deleteToDoItemById_success() throws Exception {

        when(toDoListService.deleteToDoItemById(any(UUID.class))).thenReturn(1);

        mockMvc.perform(delete("/todolist/deleteToDoItem/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void updateToDoItemById_methodTypeNotAllowed() throws Exception {
        mockMvc.perform(get("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Request method 'GET' is not supported"));
    }

    @Test
    public void updateToDoItemById_invalidIdTypeProvided() throws Exception {
        mockMvc.perform(put("/todolist/updateById/{id}", "Invalid")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid UUID string: Invalid"));
    }

    @Test
    public void updateToDoItemById_emptyRequestPayload() throws Exception {

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateToDoItemById_invalidRequestPayload() throws Exception {

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"SomeTitle\",\"description\": \"SomeDescription\"\"status\": \"PENDING\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("JSON parse error: Unexpected character ('\"' (code 34)): was expecting comma to separate Object entries"));
    }

    @Test
    public void updateToDoItemById_titleIsMissing() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", null, "SomeDescription");

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title is mandatory"));
    }

    @Test
    public void updateToDoItemById_titleIsEmpty() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "", "SomeDescription");

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title is mandatory"));
    }

    @Test
    public void updateToDoItemById_descriptionIsMissing() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", null);

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Description is mandatory"));
    }

    @Test
    public void updateToDoItemById_descriptionIsEmpty() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", "");

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Description is mandatory"));
    }

    @Test
    public void updateToDoItemById_statusMissing() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, null, "SomeTitle", "SomeDescription");

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("itemStatus not valid can be - PENDING, COMPLETED, IN_PROGRESS"));
    }

    @Test
    public void updateToDoItemById_statusIsEmpty() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "", "SomeTitle", "SomeDescription");

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("itemStatus not valid can be - PENDING, COMPLETED, IN_PROGRESS"));
    }

    @Test
    public void updateToDoItemById_statusIsInvalid() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "BLAH", "SomeTitle", "SomeDescription");

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("itemStatus not valid can be - PENDING, COMPLETED, IN_PROGRESS"));
    }

    @Test
    public void updateToDoItemById_noDoItemWithProvidedId() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", "SomeDescription");

        when(toDoListService.updateToDoItemById(any(UUID.class), anyString(), anyString(), anyString())).thenReturn(0);

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No TODO item found with provided Id"));
    }

    @Test
    public void updateToDoItemById_success() throws Exception {

        ToDoItem toDoItem = ToDoListServiceTest.createItem(id, "PENDING", "SomeTitle", "SomeDescription");

        when(toDoListService.updateToDoItemById(any(UUID.class), anyString(), anyString(), anyString())).thenReturn(1);

        when(toDoListService.getToDoItemById(any(UUID.class))).thenReturn(Optional.of(toDoItem));

        mockMvc.perform(put("/todolist/updateById/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(toDoItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value(toDoItem.getTitle()))
                .andExpect(jsonPath("$.description").value(toDoItem.getDescription()))
                .andExpect(jsonPath("$.status").value(toDoItem.getStatus()));
    }

}