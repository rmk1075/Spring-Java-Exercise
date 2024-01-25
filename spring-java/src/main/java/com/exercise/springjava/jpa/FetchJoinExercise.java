package com.exercise.springjava.jpa;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.exercise.springjava.jpa.dto.TeamDto;
import com.exercise.springjava.jpa.repository.TeamRepository;

import jakarta.transaction.Transactional;

@Service
public class FetchJoinExercise {
  private TeamRepository teamRepository;

  public FetchJoinExercise(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  // join
  @Transactional
  public TeamDto getTeamDtoByName(@NonNull String name) {
    return teamRepository.findByName(name).map(TeamDto::new).orElseThrow(() -> new RuntimeException("Team not found"));
  }

  // fetch join
  @Transactional
  public TeamDto getTeamDtoByNameUsingFetchJoin(@NonNull String name) {
    return teamRepository.findByNameUsingFetchJoin(name).map(TeamDto::new).orElseThrow(() -> new RuntimeException("Team not found"));
  }
}
