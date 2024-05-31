package com.practice.spring_jpa.jpa;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.practice.spring_jpa.jpa.entity.Item;
import com.practice.spring_jpa.jpa.entity.Member;
import com.practice.spring_jpa.jpa.entity.Team;
import com.practice.spring_jpa.jpa.repository.ItemRepository;
import com.practice.spring_jpa.jpa.repository.MemberRepository;
import com.practice.spring_jpa.jpa.repository.TeamRepository;

@SpringBootTest
public class LazyInitializationExceptionExerciseTest {
  @Autowired
  private LazyInitializationExceptionExercise lazyInitializationExceptionExercise;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TeamRepository teamRepository;

  private static final String TEAM_NAME_1 = "teamA";
  private static final String TEAM_NAME_2 = "teamB";

  @BeforeEach
  void setUp() throws Exception {
    Team team = teamRepository.save(new Team(TEAM_NAME_1));
    Member member = memberRepository.save(
      new Member(UUID.randomUUID().toString(), team));
    itemRepository.saveAll(Objects.requireNonNull(List.of(
      new Item(UUID.randomUUID().toString(), member),
      new Item(UUID.randomUUID().toString(), member),
      new Item(UUID.randomUUID().toString(), member)
    )));

    member = memberRepository.save(new Member(UUID.randomUUID().toString(), team));
    itemRepository.saveAll(Objects.requireNonNull(List.of(
      new Item(UUID.randomUUID().toString(), member),
      new Item(UUID.randomUUID().toString(), member),
      new Item(UUID.randomUUID().toString(), member)
    )));

    team = teamRepository.save(new Team(TEAM_NAME_2));
    member = memberRepository.save(new Member(UUID.randomUUID().toString(), team));
    itemRepository.saveAll(Objects.requireNonNull(List.of(
      new Item(UUID.randomUUID().toString(), member),
      new Item(UUID.randomUUID().toString(), member),
      new Item(UUID.randomUUID().toString(), member)
    )));
    System.out.println("-----------------------------------");
  }

  @AfterEach
  void tearDown() throws Exception {
    System.out.println("-----------------------------------");
    teamRepository.deleteAll();
  }

  @Test
  void testGetLazyException() {
    assertThrows(
      LazyInitializationException.class,
      () -> lazyInitializationExceptionExercise.getMembers()
    );
  }
}
