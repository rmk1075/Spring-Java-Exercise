package com.practice.spring_jpa.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practice.spring_jpa.jpa.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
  public Optional<Team> findByName(String name);

  // @Query("select t from Team t join fetch t.members m join fetch m.items i where t.name = :name")
  @Query("select t from Team t join fetch t.members m where t.name = :name")
  public Optional<Team> findByNameWithFetchJoin(String name);
}
