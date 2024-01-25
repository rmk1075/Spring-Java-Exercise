package com.exercise.springjava.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.springjava.jpa.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

}