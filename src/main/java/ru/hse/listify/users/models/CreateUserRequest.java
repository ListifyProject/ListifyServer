package ru.hse.listify.users.models;

import lombok.Data;

@Data
public class CreateUserRequest {
  private String name;
  private String password;
}