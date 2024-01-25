package com.exercise.springjava.jpa.dto;

import com.exercise.springjava.jpa.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
  private Long memberId;
  private String name;
  private String team;

  public MemberDto(Member member) {
    this.memberId = member.getMemberId();
    this.name = member.getName();
    this.team = member.getTeam().getName();
  }
}
