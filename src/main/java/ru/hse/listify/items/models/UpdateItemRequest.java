package ru.hse.listify.items.models;

import lombok.Data;

@Data
public class UpdateItemRequest {
  private String title;
  private Boolean isCompleted;
}