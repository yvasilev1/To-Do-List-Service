package org.example.todo.list.dao;


import org.example.todo.list.domain.ItemStatus;
import org.example.todo.list.domain.ToDoItem;
import org.example.todo.list.service.ToDoListServiceTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToDoListRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16.2-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void updateById_success() {
        ToDoItem toDoItem = ToDoListServiceTest.createItem(null, ItemStatus.PENDING.name(), "SomeTitle", "SomeDescription");

        UUID id = testEntityManager.persistAndFlush(toDoItem).getId();

        int updatedItems = toDoListRepository.updateById("NewTitle", "NewDescription", ItemStatus.IN_PROGRESS.name(), id);

        assertThat(updatedItems).isEqualTo(1);

        testEntityManager.clear();

        Optional<ToDoItem> updatedToDoItem = toDoListRepository.findById(id);

        assertThat(updatedToDoItem).isPresent();
        assertThat(updatedToDoItem.get().getStatus()).isEqualTo(ItemStatus.IN_PROGRESS.name());
        assertThat(updatedToDoItem.get().getTitle()).isEqualTo("NewTitle");
        assertThat(updatedToDoItem.get().getDescription()).isEqualTo("NewDescription");
    }

    @Test
    public void deleteToDoItemById_success() {
        ToDoItem toDoItem = ToDoListServiceTest.createItem(null, "PENDING", "SomeTitle", "SomeDescription");

        UUID id = testEntityManager.persistAndFlush(toDoItem).getId();

        int deletedItems = toDoListRepository.deleteToDoItemById(id);

        assertThat(deletedItems).isEqualTo(1);

        testEntityManager.clear();

        Optional<ToDoItem> deletedToDoItem = toDoListRepository.findById(id);

        assertThat(deletedToDoItem).isNotPresent();
    }

}