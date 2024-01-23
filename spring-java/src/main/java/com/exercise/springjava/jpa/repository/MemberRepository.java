package com.exercise.springjava.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.springjava.jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
