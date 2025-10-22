package ru.hse.listify.todolists.models;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class CreateToDoListRequest {
  private String title;
  private UUID authorId;
}