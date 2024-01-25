package com.exercise.springjava.jpa.dto;

import com.exercise.springjava.jpa.entity.Item;
import com.exercise.springjava.jpa.entity.Member;

import lombok.Data;

@Data
public class ItemDto {
  private Long itemId;

  private String name;
  private String member;
  private String team;

  public ItemDto(Item item) {
    this.itemId = item.getItemId();
    this.name = item.getName();

    Member member = item.getMember();
    this.member = member.getName();
    this.team = member.getTeam().getName();
  }
}
