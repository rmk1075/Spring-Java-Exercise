package com.exercise.springjava.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member {
  private Long id;
  private String name;

  @ManyToOne
  private Team team;
}
