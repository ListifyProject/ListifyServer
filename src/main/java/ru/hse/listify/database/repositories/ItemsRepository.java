package ru.hse.listify.database.repositories;

import ru.hse.listify.database.models.Item;
import ru.hse.listify.database.models.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemsRepository extends JpaRepository<Item, UUID> {
  List<Item> findByToDoList(ToDoList toDoList);
}