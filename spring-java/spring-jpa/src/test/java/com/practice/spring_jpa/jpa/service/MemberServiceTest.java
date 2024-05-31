package com.practice.spring_jpa.jpa.service;

import java.util.List;
import java.util.Objects;

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
public class MemberServiceTest {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private MemberService memberService;

  private static final String TEAM_NAME_1 = "teamA";
  private static final String MEMBER_1 = "member1";
  private static final String ITEM_1 = "item1";
  private static final String ITEM_2 = "item2";
  private static final String ITEM_3 = "item3";
  private static final String MEMBER_2 = "member2";
  private static final String ITEM_4 = "item4";
  private static final String ITEM_5 = "item5";
  private static final String ITEM_6 = "item6";

  private static final String TEAM_NAME_2 = "teamB";
  private static final String MEMBER_3 = "member3";
  private static final String ITEM_7 = "item7";
  private static final String ITEM_8 = "item8";
  private static final String ITEM_9 = "item9";


  @BeforeEach
  void setUp() throws Exception {
    // teamA
    Team team = teamRepository.save(new Team(TEAM_NAME_1));

    // teamA - member1
    Member member = memberRepository.save(
      new Member(MEMBER_1, team));
    itemRepository.saveAll(Objects.requireNonNull(List.of(
      new Item(ITEM_1, member),
      new Item(ITEM_2, member),
      new Item(ITEM_3, member)
    )));

    // teamA - member2
    member = memberRepository.save(new Member(MEMBER_2, team));
    itemRepository.saveAll(Objects.requireNonNull(List.of(
      new Item(ITEM_4, member),
      new Item(ITEM_5, member),
      new Item(ITEM_6, member)
    )));

    // teamB
    team = teamRepository.save(new Team(TEAM_NAME_2));

    // teamB - member3
    member = memberRepository.save(new Member(MEMBER_3, team));
    itemRepository.saveAll(Objects.requireNonNull(List.of(
      new Item(ITEM_7, member),
      new Item(ITEM_8, member),
      new Item(ITEM_9, member)
    )));
    System.out.println("-----------------------------------");
  }

  @AfterEach
  void tearDown() throws Exception {
    System.out.println("-----------------------------------");
    teamRepository.deleteAll();
  }

  @Test
  public void testGetMembers() {
    // 6 queries
    memberService.findAll().forEach(System.out::println);
    printLine();

    // 6 queries
    memberService.findAllWithOutFetchJoin().forEach(System.out::println);
    printLine();

    // 1 query
    memberService.findAllWithFetchJoin().forEach(System.out::println);
    printLine();
  }

  private void printLine() {
    System.out.println("-----------------------------------");
  }
}
