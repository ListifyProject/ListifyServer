package ru.hse.listify.todolists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hse.listify.database.models.ToDoList;
import ru.hse.listify.database.repositories.ToDoListsRepository;
import ru.hse.listify.todolists.models.CreateToDoListRequest;
import ru.hse.listify.todolists.models.ToDoListDTO;
import ru.hse.listify.todolists.models.UpdateToDoListRequest;
import ru.hse.listify.users.UsersService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoListsService {

  private final ToDoListsRepository toDoListRepository;
  private final UsersService userService;

  public ToDoListDTO createToDoList(CreateToDoListRequest request) {
    ToDoList toDoList = ToDoList.builder()
        .title(request.getTitle())
        .createdBy(request.getAuthorId())
        .editorIds(List.of(request.getAuthorId()))
        .build();

    ToDoList savedToDoList = toDoListRepository.save(toDoList);
    return convertToDTO(savedToDoList);
  }

  public List<ToDoListDTO> getAllToDoLists() {
    return toDoListRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public ToDoListDTO getToDoListById(UUID id) {
    ToDoList toDoList = toDoListRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ToDoList not found with id: " + id));
    return convertToDTO(toDoList);
  }

  public List<ToDoListDTO> getToDoListsByUser(UUID userId) {
    return toDoListRepository.findByEditorIdsContaining(userId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public ToDoListDTO updateToDoList(UUID id, UpdateToDoListRequest request) {
    ToDoList toDoList = toDoListRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ToDoList not found with id: " + id));

    if (request.getTitle() != null && !request.getTitle().isEmpty()) {
      toDoList.setTitle(request.getTitle());
    }

    if (request.getEditorIds() != null) {
      toDoList.setEditorIds(request.getEditorIds());
    }

    ToDoList updatedToDoList = toDoListRepository.save(toDoList);
    return convertToDTO(updatedToDoList);
  }

  public void deleteToDoList(UUID id) {
    if (!toDoListRepository.existsById(id)) {
      throw new RuntimeException("ToDoList not found with id: " + id);
    }
    toDoListRepository.deleteById(id);
  }

  public ToDoListDTO addEditor(UUID listId, UUID userId) {
    ToDoList toDoList = toDoListRepository.findById(listId)
        .orElseThrow(() -> new RuntimeException("ToDoList not found with id: " + listId));

    if (!toDoList.getEditorIds().contains(userId)) {
      toDoList.getEditorIds().add(userId);
      ToDoList updatedToDoList = toDoListRepository.save(toDoList);
      return convertToDTO(updatedToDoList);
    }

    return convertToDTO(toDoList);
  }

  public boolean userCanEditList(UUID userId, UUID listId) {
    ToDoList toDoList = toDoListRepository.findById(listId)
        .orElseThrow(() -> new RuntimeException("ToDoList not found with id: " + listId));

    return toDoList.getCreatedBy().equals(userId) ||
        toDoList.getEditorIds().contains(userId);
  }

  private ToDoListDTO convertToDTO(ToDoList toDoList) {
    ToDoListDTO dto = new ToDoListDTO();
    dto.setId(toDoList.getId());
    dto.setTitle(toDoList.getTitle());
    dto.setCreatedBy(toDoList.getCreatedBy());

    try {
      String authorName = userService.getUserById(toDoList.getCreatedBy()).getName();
      dto.setCreatedByName(authorName);
    } catch (RuntimeException e) {
      dto.setCreatedByName("Unknown User");
    }

    dto.setEditorIds(toDoList.getEditorIds());
    dto.setCreatedAt(toDoList.getCreatedAt());

    int totalItems = toDoListRepository.findByListIdIn(List.of(toDoList.getId())).size();
    int completedItems = (int) toDoListRepository.findByListIdIn(List.of(toDoList.getId())).stream()
        .filter(item -> Boolean.TRUE.equals(item.getIsCompleted()))
        .count();

    dto.setTotalItems(totalItems);
    dto.setCompletedItems(completedItems);

    return dto;
  }

  public ToDoList getToDoListEntity(UUID id) {
    return toDoListRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ToDoList not found with id: " + id));
  }
}