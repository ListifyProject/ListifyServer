package ru.hse.listify.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {
  private UUID id;
  private String name;
  private List<UUID> accessibleListIds;
}