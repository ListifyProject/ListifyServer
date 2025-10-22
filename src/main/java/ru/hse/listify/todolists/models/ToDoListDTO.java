package ru.hse.listify.todolists.models;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ToDoListDTO {
  private UUID id;
  private String title;
  private UUID createdBy;
  private String createdByName;
  private List<UUID> editorIds;
  private LocalDateTime createdAt;
  private Integer totalItems;
  private Integer completedItems;
}