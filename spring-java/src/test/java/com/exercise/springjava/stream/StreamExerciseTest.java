package com.exercise.springjava.stream;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StreamExerciseTest {

  StreamExercise streamExerciseService;

  public StreamExerciseTest() {
    streamExerciseService = new StreamExercise();
  }

  @Test
  void testSquare() {
    System.out.println(streamExerciseService.square(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    System.out.println(streamExerciseService.square(List.of()));
  }

  @Test
  void testConcat() {
    System.out.println(
        streamExerciseService.concat(List.of("Hello", " ", "World", " ", "Java", " ", "Stream")));
    System.out.println(streamExerciseService.concat(List.of()));        
  }

  @Test
  void testDistinct() {
    List<String> strings = List.of("a", "a", "b", "b", "b");
    System.out.println(strings.toString());
    System.out.println(streamExerciseService.distinct(strings).toString());
  }

  @Test
  void testFilter() {
    System.out.println(streamExerciseService.filter(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), n -> n % 2 == 0));
  }

  @Test
  void testAllMatch() {
    System.out.println(streamExerciseService.allMatch(List.of(2, 4, 6, 8, 10), n -> n % 2 == 0));
    System.out.println(streamExerciseService.allMatch(List.of(1, 2, 4, 6, 8, 10), n -> n % 2 == 0));
  }

  @Test
  void testNonNull() {
    List<String> strings = Arrays.asList("a", "a", "b", "b", "b", null, null);
    System.out.println(strings.toString());
    System.out.println(streamExerciseService.nonNull(strings).toString());
  }

  @Test
  void testGrougByStringIntegers() {
    streamExerciseService.grougByStringIntegers(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
  }
}
