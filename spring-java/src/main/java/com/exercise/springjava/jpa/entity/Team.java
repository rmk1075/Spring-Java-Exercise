package com.exercise.springjava.jpa.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Team {
  private Long id;
  private String name;
}
