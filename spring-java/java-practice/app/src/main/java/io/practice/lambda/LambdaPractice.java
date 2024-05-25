package io.practice.lambda;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class LambdaPractice {

  public List<Long> filterValue(List<Long> numbers, Long value) {
    List<Long> result = new LinkedList<>();
    for (Long number : numbers) {
      if (number > value) {
        result.add(number);
      }
    }
    return result;
  }

  class NumberFilter {
    private Long value;

    NumberFilter(Long value) {
      this.value = value;
    }

    public boolean checkValue(Long number) {
      return number > value;
    }
  }

  public List<Long> filterValueWithFilterInstance(List<Long> numbers, NumberFilter filterNumber) {
    List<Long> result = new LinkedList<>();
    for (Long number : numbers) {
      if (filterNumber.checkValue(number)) {
        result.add(number);
      }
    }
    return result;
  }

  public List<Long> filterValueWithPredicate(List<Long> numbers, Predicate<Long> predicate) {
    List<Long> result = new LinkedList<>();
    for (Long number : numbers) {
      if (predicate.test(number)) {
        result.add(number);
      }
    }
    return result;
  }

  public List<Long> filterValueWithStream(List<Long> numbers, Predicate<Long> predicate) {
    return numbers.stream().filter(predicate).toList();
  }

  public void prints(int a, int b) {
    System.out.println("a: " + a + ", b: " + b);
  }

  public void lambdaAccessLocalVariable() {
    int number = 1; // effectively final
    // number = 2; // error
    Runnable runnable = () -> System.out.println(number);
    runnable.run();
  }
}
