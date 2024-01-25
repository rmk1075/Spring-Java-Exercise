package com.exercise.springjava.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<Item> items = new ArrayList<>();

  public Member() {}

  public Member(String name, Team team) {
    this.name = name;
    this.team = team;
  }
}
