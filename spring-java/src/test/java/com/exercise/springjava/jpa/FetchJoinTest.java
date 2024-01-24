package com.exercise.springjava.jpa;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercise.springjava.jpa.entity.Member;
import com.exercise.springjava.jpa.entity.Team;
import com.exercise.springjava.jpa.repository.MemberRepository;
import com.exercise.springjava.jpa.repository.TeamRepository;

@SpringBootTest
public class FetchJoinTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TeamRepository teamRepository;

  @BeforeEach
  void setUp() throws Exception {
    Team team = teamRepository.save(new Team("teamA"));
    memberRepository.saveAll(Objects.requireNonNull(List.of(
      new Member("member1", team),
      new Member("member2", team),
      new Member("member3", team)
    )));
  }

  @Test
  void testFetchJoin() {
    List<Team> teams = teamRepository.findAll();
    assert teams.size() == 1;

    List<Member> members = memberRepository.findAll();
    assert members.size() == 3;
  }
}
