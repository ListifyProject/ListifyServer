package ru.hse.listify.database.repositories;

import ru.hse.listify.database.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemsRepository extends JpaRepository<Item, UUID> {

  List<Item> findByToDoList(UUID toDoListId);

  List<Item> findByToDoListAndIsCompleted(UUID toDoListId, Boolean isCompleted);

  long countByToDoList(UUID toDoListId);

  long countByToDoListAndIsCompleted(UUID toDoListId, Boolean isCompleted);

  @Query("SELECT i FROM Item i WHERE i.toDoList IN :toDoListIds")
  List<Item> findByToDoListIn(@Param("toDoListIds") List<UUID> toDoListIds);

  @Modifying
  @Query("DELETE FROM Item i WHERE i.toDoList = :toDoListId")
  void deleteByToDoList(@Param("toDoListId") UUID toDoListId);
}