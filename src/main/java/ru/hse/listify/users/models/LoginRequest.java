package ru.hse.listify.users.models;

import lombok.Data;

@Data
public class LoginRequest {
  private String name;
  private String password;
}