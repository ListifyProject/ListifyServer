package ru.hse.listify.items;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hse.listify.database.models.Item;
import ru.hse.listify.database.repositories.ItemsRepository;
import ru.hse.listify.items.models.CreateItemRequest;
import ru.hse.listify.items.models.ItemDTO;
import ru.hse.listify.items.models.UpdateItemRequest;
import ru.hse.listify.todolists.ToDoListsService;
import ru.hse.listify.users.UsersService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemsService {

  private final ItemsRepository itemsRepository;
  private final ToDoListsService toDoListsService;
  private final UsersService usersService;

  public ItemDTO createItem(CreateItemRequest request) {
    if (!toDoListsService.userCanEditList(request.getAuthorId(), request.getListId())) {
      throw new RuntimeException("User cannot edit this list");
    }

    Item item = Item.builder()
        .toDoList(request.getListId())
        .title(request.getTitle())
        .isCompleted(false)
        .createdBy(request.getAuthorId())
        .build();

    Item savedItem = itemsRepository.save(item);
    return convertToDTO(savedItem);
  }

  public List<ItemDTO> getAllItems() {
    return itemsRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public ItemDTO getItemById(UUID id) {
    Item item = itemsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    return convertToDTO(item);
  }

  public List<ItemDTO> getItemsByListId(UUID listId) {
    return itemsRepository.findByToDoList(listId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public List<ItemDTO> getCompletedItemsByListId(UUID listId) {
    return itemsRepository.findByToDoListAndIsCompleted(listId, true).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public List<ItemDTO> getPendingItemsByListId(UUID listId) {
    return itemsRepository.findByToDoListAndIsCompleted(listId, false).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public ItemDTO updateItem(UUID id, UpdateItemRequest request) {
    Item item = itemsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

    if (!toDoListsService.userCanEditList(item.getCreatedBy(), item.getToDoList())) {
      throw new RuntimeException("User cannot edit this item");
    }

    if (request.getTitle() != null && !request.getTitle().isEmpty()) {
      item.setTitle(request.getTitle());
    }

    if (request.getIsCompleted() != null) {
      item.setIsCompleted(request.getIsCompleted());
    }

    Item updatedItem = itemsRepository.save(item);
    return convertToDTO(updatedItem);
  }

  public ItemDTO toggleItemCompletion(UUID id) {
    Item item = itemsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

    if (!toDoListsService.userCanEditList(item.getCreatedBy(), item.getToDoList())) {
      throw new RuntimeException("User cannot edit this item");
    }

    item.setIsCompleted(!item.getIsCompleted());
    Item updatedItem = itemsRepository.save(item);
    return convertToDTO(updatedItem);
  }

  public void deleteItem(UUID id) {
    Item item = itemsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

    if (!toDoListsService.userCanEditList(item.getCreatedBy(), item.getToDoList())) {
      throw new RuntimeException("User cannot delete this item");
    }

    itemsRepository.deleteById(id);
  }

  public void deleteItemsByListId(UUID listId) {
    itemsRepository.deleteByToDoList(listId);
  }

  public long getItemCountByListId(UUID listId) {
    return itemsRepository.countByToDoList(listId);
  }

  public long getCompletedItemCountByListId(UUID listId) {
    return itemsRepository.countByToDoListAndIsCompleted(listId, true);
  }

  private ItemDTO convertToDTO(Item item) {
    ItemDTO dto = new ItemDTO();
    dto.setId(item.getId());
    dto.setListId(item.getToDoList());
    dto.setTitle(item.getTitle());
    dto.setIsCompleted(item.getIsCompleted());
    dto.setCreatedBy(item.getCreatedBy());
    dto.setCreatedAt(item.getCreatedAt());

    try {
      String createdByName = usersService.getUserById(item.getCreatedBy()).getName();
      dto.setCreatedByName(createdByName);
    } catch (RuntimeException e) {
      dto.setCreatedByName("Unknown User");
    }

    return dto;
  }
}