package com.practice.spring_jpa.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practice.spring_jpa.jpa.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
  @Query("select i from Item i join fetch i.member m join fetch m.team")
  public List<Item> findAllWithFetchJoin();
}
