package ex.practice.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Predicate;

public class StreamPractice {

  public int sum(List<Integer> numbers) {
    return numbers.stream().mapToInt(Integer::intValue).sum();
  }

  public int sumStrings(List<String> numbers) {
    return numbers.stream().mapToInt(Integer::parseInt).sum();
  }

  public double average(List<Double> numbers) {
    return numbers.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
  }

  public long count(List<Integer> numbers, int number) {
    return numbers.stream().filter(n -> n == number).count();
  }

  public List<Integer> square(List<Integer> numbers) {
    // return numbers.stream().map(n -> n * n).toList(); // unmofiable list
    return numbers.stream().map(n -> n * n).collect(Collectors.toList());
  }

  public int max(List<Integer> numbers) throws NoSuchElementException {
    return numbers.stream().mapToInt(Integer::intValue).max().getAsInt();
  }

  public String concat(List<String> strings) {
    return strings.stream().collect(Collectors.joining());
  }

  public List<String> distinct(List<String> strings) {
    return strings.stream().distinct().collect(Collectors.toList());
  }

  public List<Integer> filter(List<Integer> numbers, Predicate<Integer> predicate) {
    return numbers.stream().filter(n -> predicate.test(n)).collect(Collectors.toList());
  }

  public boolean allMatch(List<Integer> numbers, Predicate<Integer> predicate) {
    return numbers.stream().allMatch(n -> predicate.test(n));
  }

  public List<?> nonNull(List<?> list) {
    return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
  }

  public List<Integer> findIntersection(List<Integer> list1, List<Integer> list2) {
    return list1.stream().filter(list2::contains).collect(Collectors.toList());
  }

  public void grougByStringIntegers(List<String> list) {
    System.out.println(
      list.stream().map(Integer::parseInt).collect(Collectors.groupingBy(n -> n % 2 == 0)));
    Map<Boolean, Long> map = list
      .stream()
      .map(Integer::parseInt)
      .collect(Collectors.groupingBy(n -> n % 2 == 0, Collectors.counting()));
    System.out.println(map);

    map = list.stream()
      .map(Integer::parseInt)
      .collect(Collectors.groupingBy(
        n -> n % 2 == 0,
        () -> new TreeMap<>(Comparator.comparing(Boolean::booleanValue)), // return map type
        Collectors.counting()));
    System.out.println(map);
  }
}
