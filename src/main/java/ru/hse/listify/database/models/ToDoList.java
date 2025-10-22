package ru.hse.listify.database.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToDoList {

  @Id
  @UuidGenerator
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String title;

  @Column(name = "created_by", nullable = false)
  private UUID createdBy;

  @Column(name = "editor_ids")
  @Builder.Default
  private List<UUID> editorIds = new ArrayList<>();

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
}