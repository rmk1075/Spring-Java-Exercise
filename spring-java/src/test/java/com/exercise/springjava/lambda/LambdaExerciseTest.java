package com.exercise.springjava.lambda;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LambdaExerciseTest {

  private final LambdaExercise lambdaExercise = new LambdaExercise();
  private static List<Long> numbers = new LinkedList<>();
  private static Long limit = 100L;
  private Long startTime;

  @BeforeAll
  static void setUpAll() {
    Random random = new Random();
    int size = 5000000;
    for (int i = 0; i < size; i++) {
      numbers.add(random.nextLong(10000L));
    }
  }

  @BeforeEach
  void setUp() {
    startTime = System.currentTimeMillis();
  }

  @AfterEach
  void tearDown() {
    System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");
  }

  @Test
  void testFilterValue() {
    lambdaExercise.filterValue(numbers, limit);
  }

  @Test
  void testFilterValueWithFilterInstance() {
    lambdaExercise.filterValueWithFilterInstance(
      numbers, lambdaExercise.new NumberFilter(limit));
  }

  @Test
  void testFilterValueWithPredicate() {
    lambdaExercise.filterValueWithPredicate(
      numbers, number -> number > limit);
  }

  @Test
  void testFilterValueWithStream() {
    lambdaExercise.filterValueWithStream(
      numbers, number -> number > limit);
  }

  @Test
  void testPrints() {
    int num1 = 1;
    int num2 = 2;
    lambdaExercise.prints(num1, num2);

    System.out.println("--------------------");

    MyFunction<Integer, Integer> func = (a, b) -> {
      System.out.println("a: " + a + ", b: " + b);
    };
    func.apply(num1, num2);
  }
}

@FunctionalInterface
interface MyFunction<A, B> {
  void apply(A a, B b);
}
