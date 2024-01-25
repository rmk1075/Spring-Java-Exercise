package com.exercise.springjava.jpa.dto;

import java.util.List;

import com.exercise.springjava.jpa.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
  private Long memberId;
  private String name;
  private String team;
  private List<String> items;

  public MemberDto(Member member) {
    this.memberId = member.getMemberId();
    this.name = member.getName();
    this.team = member.getTeam().getName();
    this.items = member.getItems().stream().map(item -> item.getName()).toList();
  }
}
