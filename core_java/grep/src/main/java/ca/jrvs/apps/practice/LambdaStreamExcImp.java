package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {
  
  @Override
  public Stream<String> createStrStream(String ... strings) {

    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {

    return createStrStream(strings).map(String::toUpperCase);
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {

    return stringStream.filter(character -> !character.equals(pattern));
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {

    return intStream.mapToDouble(value -> Math.pow(value, 0.5));
//    DoubleStream doubleStream = intStream.asDoubleStream();
//    doubleStream.forEach(value -> Math.pow(value, 0.5));
//    return doubleStream;
  }

  @Override
  public IntStream getOdd(IntStream intStream) {

    return intStream.filter((number) -> number % 2 != 0);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return (word) -> System.out.println(prefix + word + suffix);
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    for (int i = 0; i < messages.length; i++) {
      printer.accept(messages[i]);
    }
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    intStream.forEach((number) -> printer.accept(Integer.toString(number)));
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {

    // [[1,2,3,4], [1,2,3], [1,2,4,6]]
    Stream<Integer> flattened = ints.flatMap(Collection::stream);
    flattened.map(value -> Math.pow(value, 2));
    return flattened;
  }
}
