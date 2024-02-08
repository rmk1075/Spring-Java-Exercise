package com.exercise.springjava.jpa;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.exercise.springjava.jpa.dto.ItemDto;
import com.exercise.springjava.jpa.dto.MemberDto;
import com.exercise.springjava.jpa.dto.TeamDto;
import com.exercise.springjava.jpa.repository.ItemRepository;
import com.exercise.springjava.jpa.repository.MemberRepository;
import com.exercise.springjava.jpa.repository.TeamRepository;

import jakarta.transaction.Transactional;

@Service
public class FetchJoinExercise {
  private TeamRepository teamRepository;
  private MemberRepository memberRepository;
  private ItemRepository itemRepository;

  public FetchJoinExercise(
    TeamRepository teamRepository,
    MemberRepository memberRepository,
    ItemRepository itemRepository
  ) {
    this.teamRepository = teamRepository;
    this.memberRepository = memberRepository;
    this.itemRepository = itemRepository;
  }

  // join
  @Transactional
  public TeamDto getTeamDtoByName(@NonNull String name) {
    return teamRepository.findByName(name).map(TeamDto::new).orElseThrow(() -> new RuntimeException("Team not found"));
  }

  @Transactional
  public List<MemberDto> getMemberDtoList() {
    return memberRepository.findAll().stream().map(MemberDto::new).toList();
  }

  @Transactional
  public List<ItemDto> getItemDtoList() {
    return itemRepository.findAll().stream().map(ItemDto::new).toList();
  }

  @Transactional
  public List<MemberDto> getMemberDtoListWithoutFetchJoin() {
    return memberRepository.findAllWithOutFetchJoin().stream().map(MemberDto::new).toList();
  }

  // fetch join
  @Transactional
  public TeamDto getTeamDtoByNameWithFetchJoin(@NonNull String name) {
    return teamRepository.findByNameWithFetchJoin(name).map(TeamDto::new).orElseThrow(() -> new RuntimeException("Team not found"));
  }

  @Transactional
  public List<MemberDto> getMemberDtoListWithFetchJoin() {
    return memberRepository.findAllWithFetchJoin().stream().map(MemberDto::new).toList();
  }

  @Transactional
  public List<ItemDto> getItemDtoListWithFetchJoin() {
    return itemRepository.findAllWithFetchJoin().stream().map(ItemDto::new).toList();
  }
}
