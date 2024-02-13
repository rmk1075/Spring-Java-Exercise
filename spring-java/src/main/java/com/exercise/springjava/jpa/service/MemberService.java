package com.exercise.springjava.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exercise.springjava.jpa.dto.MemberDto;
import com.exercise.springjava.jpa.entity.Member;
import com.exercise.springjava.jpa.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Transactional
  public List<MemberDto> findAll() {
    return memberRepository.findAll().stream().map(MemberDto::new).toList();
  }

  @Transactional
  public List<Member> findAllAsEntity() {
    return memberRepository.findAll();
  }

  @Transactional
  public List<MemberDto> findAllWithOutFetchJoin() {
    return memberRepository.findAllWithOutFetchJoin().stream().map(MemberDto::new).toList();
  }

  @Transactional
  public List<MemberDto> findAllWithFetchJoin() {
    return memberRepository.findAllWithFetchJoin().stream().map(MemberDto::new).toList();
  }
}
