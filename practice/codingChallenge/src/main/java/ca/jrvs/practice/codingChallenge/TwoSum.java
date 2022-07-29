package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;

public class TwoSum {

  /**
   * Big O: O(n^2)
   * Justification: You have to loop through the list twice so its n^2
   */
  public int[] solveTwoSum(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
      for (int j = 0; j < nums.length; j++) {
        if (nums[i] + nums[j] == target && i != j) {
          return new int[] {i, j};
        }
      }
    }
    return null;
  }

  /**
   * Big O: O(n)
   * Justification: You can have to go through the loop once and look up for hashmap is constant
   */
  public int[] solveTwoSumOptimized(int[] nums, int target) {
    HashMap<Integer, Integer> seen = new HashMap<Integer, Integer>();
    int num_needed = 0;
    for (int i = 0; i < nums.length; i++) {
      num_needed = target - nums[i];

      if (seen.containsKey(num_needed)) {
        return new int[] {seen.get(num_needed), i};
      }
      seen.put(nums[i], i);
    }
    return null;
  }

}
