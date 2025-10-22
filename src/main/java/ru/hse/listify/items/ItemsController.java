package ru.hse.listify.items;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.listify.items.models.CreateItemRequest;
import ru.hse.listify.items.models.ItemDTO;
import ru.hse.listify.items.models.UpdateItemRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemsController {

  private final ItemsService itemsService;

  @PostMapping
  public ResponseEntity<?> createItem(@RequestBody CreateItemRequest request) {
    try {
      ItemDTO item = itemsService.createItem(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(item);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<List<ItemDTO>> getAllItems() {
    List<ItemDTO> items = itemsService.getAllItems();
    return ResponseEntity.ok(items);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemDTO> getItemById(@PathVariable UUID id) {
    try {
      ItemDTO item = itemsService.getItemById(id);
      return ResponseEntity.ok(item);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/list/{listId}")
  public ResponseEntity<List<ItemDTO>> getItemsByListId(@PathVariable UUID listId) {
    List<ItemDTO> items = itemsService.getItemsByListId(listId);
    return ResponseEntity.ok(items);
  }

  @GetMapping("/list/{listId}/completed")
  public ResponseEntity<List<ItemDTO>> getCompletedItemsByListId(@PathVariable UUID listId) {
    List<ItemDTO> items = itemsService.getCompletedItemsByListId(listId);
    return ResponseEntity.ok(items);
  }

  @GetMapping("/list/{listId}/pending")
  public ResponseEntity<List<ItemDTO>> getPendingItemsByListId(@PathVariable UUID listId) {
    List<ItemDTO> items = itemsService.getPendingItemsByListId(listId);
    return ResponseEntity.ok(items);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateItem(
      @PathVariable UUID id,
      @RequestBody UpdateItemRequest request) {
    try {
      ItemDTO item = itemsService.updateItem(id, request);
      return ResponseEntity.ok(item);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PatchMapping("/{id}/toggle")
  public ResponseEntity<?> toggleItemCompletion(@PathVariable UUID id) {
    try {
      ItemDTO item = itemsService.toggleItemCompletion(id);
      return ResponseEntity.ok(item);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
    try {
      itemsService.deleteItem(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/list/{listId}")
  public ResponseEntity<Void> deleteItemsByListId(@PathVariable UUID listId) {
    try {
      itemsService.deleteItemsByListId(listId);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}