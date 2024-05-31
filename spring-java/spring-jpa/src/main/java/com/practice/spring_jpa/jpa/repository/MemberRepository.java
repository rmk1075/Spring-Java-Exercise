package com.practice.spring_jpa.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practice.spring_jpa.jpa.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
  @Query("select m from Member m join m.team join m.items")
  public List<Member> findAllWithOutFetchJoin();

  @Query("select m from Member m join fetch m.team join fetch m.items")
  public List<Member> findAllWithFetchJoin();
}
