package ru.hse.listify.items.models;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ItemDTO {
  private UUID id;
  private UUID listId;
  private String title;
  private Boolean isCompleted;
  private UUID createdBy;
  private String createdByName;
  private LocalDateTime createdAt;
}