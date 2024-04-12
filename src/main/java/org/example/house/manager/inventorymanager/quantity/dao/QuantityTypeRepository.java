package org.example.house.manager.inventorymanager.quantity.dao;

import org.example.house.manager.inventorymanager.quantity.domain.QuantityType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuantityTypeRepository extends CrudRepository<QuantityType, UUID> {

    @Query("select (count(q) > 0) from QuantityType q where upper(q.name) = upper(?1)")
    boolean existsByName(@NonNull String name);

    @Transactional
    @Modifying
    @Query("delete from QuantityType q where upper(q.name) = upper(?1)")
    int deleteQuantityTypeByName(@NonNull String name);

    @Query("select q from QuantityType q where upper(q.name) = upper(?1)")
    @NonNull
    Optional<QuantityType> findQuantityTypeByName(@NonNull String name);

    @Transactional
    @Modifying
    @Query("update QuantityType q set q.name = ?1 where upper(q.name) = upper(?2)")
    int updateQuantityTypeByName(@NonNull String newName, @NonNull String name);


}