package org.example.house.manager.inventorymanager.inventoryitemtype.dao;

import org.example.house.manager.inventorymanager.inventoryitemtype.domain.InventoryItemType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryItemTypeRepository extends CrudRepository<InventoryItemType, UUID> {

    @Query("select (count(i) > 0) from InventoryItemType i where upper(i.name) = upper(?1)")
    boolean existsByName(@NonNull String name);

    @Transactional
    @Modifying
    @Query("delete from InventoryItemType i where upper(i.name) = upper(?1)")
    int deleteInventoryItemTypeByName(@NonNull String name);

    @Transactional
    @Modifying
    @Query("update InventoryItemType i set i.name = ?1 where upper(i.name) = upper(?2)")
    int updateInventoryItemTypeByName(@NonNull String newName, @NonNull String name);

    @Query("select i from InventoryItemType i where upper(i.name) = upper(?1)")
    @NonNull
    Optional<InventoryItemType> findInventoryItemTypeByName(@NonNull String name);

}