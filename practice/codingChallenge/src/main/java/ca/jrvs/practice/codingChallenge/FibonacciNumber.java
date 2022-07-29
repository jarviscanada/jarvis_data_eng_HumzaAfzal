package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;

/**
 * https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-15aa47e160294979b0b499f7e735abe0
 */
public class FibonacciNumber {

  /**
   * Big O: O(2^n)
   * Justification: It has to compute the values from the beginning everytime it is called
   */
  public int solveRecursive(int n) {

    if (n == 1 || n == 2) {
      return 1;
    }
    else {
      return solveRecursive(n-1) + solveRecursive(n-2);
    }
  }

  /**
   * Big O: O(n)
   * Justification: You only have to go through the input once
   */
  public int solveDynamic(int n) {

    if (n <= 1) {
      return n;
    }

    int[] fib_cache = new int[n + 1];
    fib_cache[1] = 1;
    for(int i = 2; i <= n; i++) {
      fib_cache[i] = fib_cache[i - 1] + fib_cache[i - 2];
    }
    return fib_cache[n];
  }
}
