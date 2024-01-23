package com.exercise.springjava.jpa;

import com.exercise.springjava.jpa.repository.MemberRepository;
import com.exercise.springjava.jpa.repository.TeamRepository;

public class FetchJoin {
  private MemberRepository memberRepository;
  private TeamRepository teamRepository;

  public FetchJoin(MemberRepository memberRepository, TeamRepository teamRepository) {
    this.memberRepository = memberRepository;
    this.teamRepository = teamRepository;
  }
}
