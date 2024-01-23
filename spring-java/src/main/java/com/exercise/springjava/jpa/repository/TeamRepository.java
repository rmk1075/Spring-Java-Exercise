package com.exercise.springjava.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.springjava.jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
  
}
