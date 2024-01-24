package com.exercise.springjava.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Team {
  @Id @GeneratedValue
  private Long id;

  private String name;

  public Team() {}

  public Team(String name) {
    this.name = name;
  }
}
