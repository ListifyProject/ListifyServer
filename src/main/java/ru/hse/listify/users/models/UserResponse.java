package ru.hse.listify.users.models;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
  private UUID id;
  private String name;
  private List<UUID> accessibleListIds;
  private Integer accessibleListsCount;

  public UserResponse(UUID id, String name, List<UUID> accessibleListIds) {
    this.id = id;
    this.name = name;
    this.accessibleListIds = accessibleListIds;
    this.accessibleListsCount = accessibleListIds != null ? accessibleListIds.size() : 0;
  }
}