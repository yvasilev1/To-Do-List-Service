package org.example.todo.list.dao;

import org.example.todo.list.domain.ItemStatus;
import org.example.todo.list.domain.ToDoItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ToDoListRepository extends CrudRepository<ToDoItem, UUID> {
    @Override
    <S extends ToDoItem> S save(S entity);

    @Transactional
    @Modifying
    @Query("update t_todoitem t set t.title = ?1, t.description = ?2, t.status = ?3 where t.id = ?4")
    int updateById(@NonNull String title, @NonNull String description, @NonNull ItemStatus status, @NonNull UUID id);

    @Override
    Optional<ToDoItem> findById(UUID uuid);

    @Override
    Iterable<ToDoItem> findAll();

    @Override
    void deleteById(UUID uuid);
}
