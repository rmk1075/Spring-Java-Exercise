package ex.practice.lambda;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LambdaPracticeTest {

  private final LambdaPractice LambdaPractice = new LambdaPractice();
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
    LambdaPractice.filterValue(numbers, limit);
  }

  @Test
  void testFilterValueWithFilterInstance() {
    LambdaPractice.filterValueWithFilterInstance(
      numbers, LambdaPractice.new NumberFilter(limit));
  }

  @Test
  void testFilterValueWithPredicate() {
    LambdaPractice.filterValueWithPredicate(
      numbers, number -> number > limit);
  }

  @Test
  void testFilterValueWithStream() {
    LambdaPractice.filterValueWithStream(
      numbers, number -> number > limit);
  }

  @Test
  void testPrints() {
    int num1 = 1;
    int num2 = 2;
    LambdaPractice.prints(num1, num2);

    System.out.println("--------------------");

    MyFunction<Integer, Integer> func = (a, b) -> {
      System.out.println("a: " + a + ", b: " + b);
    };
    func.apply(num1, num2);
  }

  @Test
  void testlambdaAccessLocalVariable() {
    assertDoesNotThrow(() -> LambdaPractice.lambdaAccessLocalVariable());
  }
}

@FunctionalInterface
interface MyFunction<A, B> {
  void apply(A a, B b);
}
