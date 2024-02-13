package com.exercise.springjava.jpa;

import org.springframework.web.bind.annotation.RestController;

import com.exercise.springjava.jpa.dto.MemberDto;
import com.exercise.springjava.jpa.entity.Item;
import com.exercise.springjava.jpa.entity.Member;
import com.exercise.springjava.jpa.entity.Team;
import com.exercise.springjava.jpa.repository.ItemRepository;
import com.exercise.springjava.jpa.repository.MemberRepository;
import com.exercise.springjava.jpa.repository.TeamRepository;
import com.exercise.springjava.jpa.service.MemberService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/jpa/lazy")
public class LazyInitializationExceptionExercise {
      private MemberService memberService;
      private TeamRepository teamRepository;
      private MemberRepository memberRepository;
      private ItemRepository itemRepository;

      public LazyInitializationExceptionExercise(
        MemberService memberService,
        TeamRepository teamRepository,
        MemberRepository memberRepository,
        ItemRepository itemRepository
      ) {
          this.memberService = memberService;
          this.teamRepository = teamRepository;
          this.memberRepository = memberRepository;
          this.itemRepository = itemRepository;
      }

      // occurs LazyInitializationException without OSIV
      @GetMapping("/members")
      public List<MemberDto> getMembers() {
          setUpMembers();
          return memberService
            .findAllAsEntity()
            .stream()
            .map(MemberDto::new)
            .toList();
      }

      private void setUpMembers() {
          String teamName = "teamA";
          Team team = teamRepository
            .findByName(teamName)
            .orElseGet(() -> teamRepository.save(new Team(teamName)));
          Member member = memberRepository.save(
            new Member(UUID.randomUUID().toString(), team));
          itemRepository.saveAll(Objects.requireNonNull(List.of(
            new Item(UUID.randomUUID().toString(), member),
            new Item(UUID.randomUUID().toString(), member),
            new Item(UUID.randomUUID().toString(), member)
          )));
      }
}
