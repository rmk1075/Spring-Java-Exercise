package com.exercise.springjava.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Getter
@Entity
public class Team {
  @Id @GeneratedValue
  private Long teamId;

  @Column(unique = true)
  private String name;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<Member> members = new ArrayList<>();

  public Team() {}

  public Team(String name) {
    this.name = name;
  }
}
