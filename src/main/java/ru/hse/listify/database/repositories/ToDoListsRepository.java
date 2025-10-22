package ru.hse.listify.database.repositories;

import ru.hse.listify.database.models.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToDoListsRepository extends JpaRepository<ToDoList, UUID> {
  @Query(value = "SELECT * FROM lists WHERE :userId = ANY(editor_ids)", nativeQuery = true)
  List<ToDoList> findByEditorIdsContaining(@Param("userId") UUID userId);
}