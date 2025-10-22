package ru.hse.listify.users;

import ru.hse.listify.users.models.CreateUserRequest;
import ru.hse.listify.users.models.LoginRequest;
import ru.hse.listify.users.models.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

  private final UsersService userService;

  @PostMapping("/register")
  public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
    try {
      UserResponse user = userService.createUser(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(user);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
      UserResponse response = userService.login(request);
      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
    try {
      UserResponse user = userService.getUserById(id);
      return ResponseEntity.ok(user);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<UserResponse> getUserByName(@PathVariable String name) {
    try {
      UserResponse user = userService.getUserByName(name);
      return ResponseEntity.ok(user);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{userId}/accessible-lists/{listId}")
  public ResponseEntity<UserResponse> addAccessibleList(
      @PathVariable UUID userId,
      @PathVariable UUID listId) {
    try {
      UserResponse user = userService.addAccessibleList(userId, listId);
      return ResponseEntity.ok(user);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{userId}/has-access/{listId}")
  public ResponseEntity<Boolean> userHasAccessToList(
      @PathVariable UUID userId,
      @PathVariable UUID listId) {
    try {
      boolean hasAccess = userService.userHasAccessToList(userId, listId);
      return ResponseEntity.ok(hasAccess);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}