package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Test;

public class TwoSumTest {

  @Test
  public void solveTwoSum() {
    int[] nums = new int[] {0, 2, 3, 4, 6};
    int target = 5;
    TwoSum tester = new TwoSum();
    int[] result = tester.solveTwoSum(nums, target);
    int[] answer = new int[] {1, 2};
    assertTrue(Arrays.equals(result, answer));
  }

  @Test
  public void solveTwoSumOptimized() {
    int[] nums = new int[] {0, 2, 3, 4, 6};
    int target = 5;
    TwoSum tester = new TwoSum();
    int[] result = tester.solveTwoSumOptimized(nums, target);
    int[] answer = new int[] {1, 2};
    assertTrue(Arrays.equals(result, answer));
  }
}