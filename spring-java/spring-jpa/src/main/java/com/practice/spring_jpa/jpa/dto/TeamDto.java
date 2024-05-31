package com.practice.spring_jpa.jpa.dto;

import java.util.List;

import com.practice.spring_jpa.jpa.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDto {
  private Long teamId;
  private String name;
  private List<MemberDto> members;

  public TeamDto(Team team) {
    this.teamId = team.getTeamId();
    this.name = team.getName();
    this.members = team.getMembers().stream().map(member -> new MemberDto(member)).toList();
  }
}
