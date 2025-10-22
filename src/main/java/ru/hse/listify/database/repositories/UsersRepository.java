package ru.hse.listify.database.repositories;

import ru.hse.listify.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {

  Optional<User> findByName(String name);
}