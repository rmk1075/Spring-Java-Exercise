package com.exercise.springjava.jpa;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercise.springjava.jpa.entity.Member;
import com.exercise.springjava.jpa.entity.Team;
import com.exercise.springjava.jpa.repository.MemberRepository;
import com.exercise.springjava.jpa.repository.TeamRepository;

@SpringBootTest
public class FetchJoinExerciseTest {

  @Autowired
  private FetchJoinExercise fetchJoinExercise;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TeamRepository teamRepository;

  private static final String TEAM_NAME_1 = "teamA";
  private static final String TEAM_NAME_2 = "teamB";

  @BeforeEach
  void setUp() throws Exception {
    Team team = teamRepository.save(new Team(TEAM_NAME_1));
    memberRepository.saveAll(Objects.requireNonNull(List.of(
      new Member(UUID.randomUUID().toString(), team),
      new Member(UUID.randomUUID().toString(), team),
      new Member(UUID.randomUUID().toString(), team),
      new Member(UUID.randomUUID().toString(), team)
    )));

    team = teamRepository.save(new Team(TEAM_NAME_2));
    memberRepository.save(new Member(UUID.randomUUID().toString(), team));
    System.out.println("-----------------------------------");
  }

  @AfterEach
  void tearDown() throws Exception {
    System.out.println("-----------------------------------");
    memberRepository.deleteAll();
    teamRepository.deleteAll();
  }

  @Test
  void testGetTeamDtoByName() {
    System.out.println(
      fetchJoinExercise.getTeamDtoByName(TEAM_NAME_1));
    System.out.println(
      fetchJoinExercise.getTeamDtoByName(TEAM_NAME_2));
  }

  @Test
  void testGetTeamDtoByNameUsingFetchJoin() {
    System.out.println(
      fetchJoinExercise.getTeamDtoByNameUsingFetchJoin(TEAM_NAME_1));
    System.out.println(
      fetchJoinExercise.getTeamDtoByNameUsingFetchJoin(TEAM_NAME_2));
  }
}
