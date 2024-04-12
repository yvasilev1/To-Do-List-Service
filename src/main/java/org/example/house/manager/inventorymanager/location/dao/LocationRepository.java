package org.example.house.manager.inventorymanager.location.dao;

import org.example.house.manager.inventorymanager.location.domain.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {

    @Transactional
    @Modifying
    @Query("delete from Location l where upper(l.name) = upper(?1)")
    int deleteLocationByName(@NonNull String name);

    @Transactional
    @Modifying
    @Query("update Location l set l.name = ?1 where upper(l.name) = upper(?2)")
    int updateByLocationName(@NonNull String newLocationName, @NonNull String name);

    @Query("select l from Location l where upper(l.name) = upper(?1) order by l.name")
    @NonNull
    Optional<Location> findByLocationName(@NonNull String name);

    @Query("select (count(l) > 0) from Location l where upper(l.name) = upper(?1)")
    boolean existsByName(@NonNull String name);
}