package org.example.todo.list.dao;


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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToDoListRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
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
    public void saveToDoItem_success() {
        ToDoItem toDoItem = ToDoListServiceTest.createItem(UUID.randomUUID(), "PENDING", "someTitle", "someDescription");

        toDoListRepository.save(toDoItem);

        assertThat(testEntityManager.find(ToDoItem.class, toDoItem.getId())).isNull();
    }

}