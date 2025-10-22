package ru.hse.listify.todolists;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.listify.todolists.models.CreateToDoListRequest;
import ru.hse.listify.todolists.models.ToDoListDTO;
import ru.hse.listify.todolists.models.UpdateToDoListRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class ToDoListsController {

  private final ToDoListsService toDoListsService;

  @PostMapping
  public ResponseEntity<?> createToDoList(@RequestBody CreateToDoListRequest request) {
    try {
      ToDoListDTO toDoList = toDoListsService.createToDoList(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(toDoList);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<List<ToDoListDTO>> getAllToDoLists() {
    List<ToDoListDTO> lists = toDoListsService.getAllToDoLists();
    return ResponseEntity.ok(lists);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ToDoListDTO> getToDoListById(@PathVariable UUID id) {
    try {
      ToDoListDTO toDoList = toDoListsService.getToDoListById(id);
      return ResponseEntity.ok(toDoList);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<ToDoListDTO>> getToDoListsByUser(@PathVariable UUID userId) {
    try {
      List<ToDoListDTO> lists = toDoListsService.getToDoListsByUser(userId);
      return ResponseEntity.ok(lists);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateToDoList(
      @PathVariable UUID id,
      @RequestBody UpdateToDoListRequest request) {
    try {
      ToDoListDTO toDoList = toDoListsService.updateToDoList(id, request);
      return ResponseEntity.ok(toDoList);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteToDoList(@PathVariable UUID id) {
    try {
      toDoListsService.deleteToDoList(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{listId}/editors/{userId}")
  public ResponseEntity<?> addEditor(
      @PathVariable UUID listId,
      @PathVariable UUID userId) {
    try {
      ToDoListDTO toDoList = toDoListsService.addEditor(listId, userId);
      return ResponseEntity.ok(toDoList);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{listId}/can-edit/{userId}")
  public ResponseEntity<Boolean> userCanEditList(
      @PathVariable UUID listId,
      @PathVariable UUID userId) {
    try {
      boolean canEdit = toDoListsService.userCanEditList(userId, listId);
      return ResponseEntity.ok(canEdit);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}