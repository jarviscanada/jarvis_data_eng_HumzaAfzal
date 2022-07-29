package ca.jrvs.practice.codingChallenge;

import java.util.Map;

/**
 * https://www.notion.so/jarvisdev/How-to-compare-two-maps-712fe69bc7934dbc8d8d8ff2c467bec0
 */
public class CompareTwoMaps {

  /**
   * Big O: O(n)
   * Justification: because looking up on a HashMap is constant and you need to loop through every value
   */
  public <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2){

    return m1.equals(m2);
  }

}