package com.exercise.springjava.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Member {
  @Id @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne
  private Team team;

  public Member() {}

  public Member(String name, Team team) {
    this.name = name;
    this.team = team;
  }
}
