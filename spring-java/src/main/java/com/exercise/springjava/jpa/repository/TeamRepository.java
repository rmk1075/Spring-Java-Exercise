package com.exercise.springjava.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exercise.springjava.jpa.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
  public Optional<Team> findByName(String name);

  @Query("select t from Team t join fetch t.members where t.name = :name")
  public Optional<Team> findByNameUsingFetchJoin(String name);
}
