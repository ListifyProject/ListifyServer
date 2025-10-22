package ru.hse.listify.items.models;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateItemRequest {
  private String title;
  private UUID listId;
  private UUID authorId;
}