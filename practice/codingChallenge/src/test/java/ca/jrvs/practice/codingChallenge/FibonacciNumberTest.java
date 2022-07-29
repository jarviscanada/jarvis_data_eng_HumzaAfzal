package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciNumberTest {

  @Test
  public void solveRecursive() {
    int number = 10;
    int answer = 55;
    FibonacciNumber tester = new FibonacciNumber();

    int result = tester.solveRecursive(number);

    assertEquals(result, answer);

  }

  @Test
  public void solveDynamic() {
    int number = 10;
    int answer = 55;
    FibonacciNumber tester = new FibonacciNumber();
    int result = tester.solveDynamic(number);
    assertEquals(result, answer);
  }
}