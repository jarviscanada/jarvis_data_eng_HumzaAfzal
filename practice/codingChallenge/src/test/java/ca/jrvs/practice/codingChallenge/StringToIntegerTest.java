package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringToIntegerTest {

  @Test
  public void solution1() {
    String test = "       -4532kflsdfklsd";
    StringToInteger tester = new StringToInteger();
    int result = tester.solution1(test);

    assertEquals(-4532, result);
  }

  @Test
  public void solution2() {
    String test = "       -4532kflsdfklsd";
    StringToInteger tester = new StringToInteger();
    int result = tester.solution2(test);

    assertEquals(-4532, result);
  }
}