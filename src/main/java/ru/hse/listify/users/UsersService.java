package ru.hse.listify.users;

import ru.hse.listify.users.models.CreateUserRequest;
import ru.hse.listify.users.models.LoginRequest;
import ru.hse.listify.users.models.UserResponse;
import ru.hse.listify.database.models.User;
import ru.hse.listify.database.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {

  private final UsersRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse createUser(CreateUserRequest request) {
    Optional<User> user = userRepository.findByName(request.getName());
    if (user.isPresent()) {
      throw new RuntimeException("User with name '" + request.getName() + "' already exists");
    }

    User savedUser = userRepository.save(User.builder()
        .name(request.getName())
        .passwordHash(passwordEncoder.encode(request.getPassword()))
        .accessibleListIds(List.of())
        .build());
    return convertToResponse(savedUser);
  }

  public UserResponse login(LoginRequest request) {
    User user = userRepository.findByName(request.getName())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
      throw new RuntimeException("Invalid password");
    }

    return new UserResponse(
        user.getId(),
        user.getName(),
        user.getAccessibleListIds()
    );
  }

  public UserResponse getUserById(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    return convertToResponse(user);
  }

  public UserResponse getUserByName(String name) {
    User user = userRepository.findByName(name)
        .orElseThrow(() -> new RuntimeException("User not found with name: " + name));
    return convertToResponse(user);
  }

  public UserResponse addAccessibleList(UUID userId, UUID listId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    if (!user.getAccessibleListIds().contains(listId)) {
      user.getAccessibleListIds().add(listId);
      User updatedUser = userRepository.save(user);
      return convertToResponse(updatedUser);
    }

    return convertToResponse(user);
  }

  public boolean userHasAccessToList(UUID userId, UUID listId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    return user.getAccessibleListIds().contains(listId);
  }

  private UserResponse convertToResponse(User user) {
    return new UserResponse(
        user.getId(),
        user.getName(),
        user.getAccessibleListIds()
    );
  }
}