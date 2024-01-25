package com.exercise.springjava.jpa;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.exercise.springjava.jpa.dto.ItemDto;
import com.exercise.springjava.jpa.dto.TeamDto;
import com.exercise.springjava.jpa.repository.ItemRepository;
import com.exercise.springjava.jpa.repository.TeamRepository;

import jakarta.transaction.Transactional;

@Service
public class FetchJoinExercise {
  private TeamRepository teamRepository;
  private ItemRepository itemRepository;

  public FetchJoinExercise(TeamRepository teamRepository, ItemRepository itemRepository) {
    this.teamRepository = teamRepository;
    this.itemRepository = itemRepository;
  }

  // join
  @Transactional
  public TeamDto getTeamDtoByName(@NonNull String name) {
    return teamRepository.findByName(name).map(TeamDto::new).orElseThrow(() -> new RuntimeException("Team not found"));
  }

  @Transactional
  public List<ItemDto> getItemDtoList() {
    return itemRepository.findAll().stream().map(ItemDto::new).toList();
  }

  // fetch join
  @Transactional
  public TeamDto getTeamDtoByNameUsingFetchJoin(@NonNull String name) {
    return teamRepository.findByNameUsingFetchJoin(name).map(TeamDto::new).orElseThrow(() -> new RuntimeException("Team not found"));
  }

  @Transactional
  public List<ItemDto> getItemDtoListUsingFetchJoin() {
    return itemRepository.findAllUsingFetchJoin().stream().map(ItemDto::new).toList();
  }
}