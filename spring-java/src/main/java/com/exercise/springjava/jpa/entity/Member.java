package com.exercise.springjava.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Member {
  @Id @GeneratedValue
  private Long memberId;

  @Column(unique = true)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teamId")
  private Team team;

  public Member() {}

  public Member(String name, Team team) {
    this.name = name;
    this.team = team;
  }
}
