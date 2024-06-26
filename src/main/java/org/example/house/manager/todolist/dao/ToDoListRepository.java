package org.example.house.manager.todolist.dao;

import org.example.house.manager.todolist.domain.ToDoItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Repository
public interface ToDoListRepository extends CrudRepository<ToDoItem, UUID> {

    @Transactional
    @Modifying
    @Query("update ToDoItem t set t.title = ?1, t.description = ?2, t.status = ?3 where t.id = ?4")
    int updateById(@NonNull String title, @NonNull String description, @NonNull String status, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("delete from ToDoItem t where t.id = ?1")
    int deleteToDoItemById(@NonNull UUID id);

}
