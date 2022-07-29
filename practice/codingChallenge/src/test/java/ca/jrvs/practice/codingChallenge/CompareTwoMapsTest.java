package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.HashMap;

public class CompareTwoMapsTest {

  @org.junit.Test
  public void compareMaps() {
    CompareTwoMaps tester = new CompareTwoMaps();
    HashMap map1 = new HashMap();
    HashMap map2 = new HashMap();

    map1.put(0, "hello");
    map1.put(1, "hi");
    map1.put("greeting", 2);

    map2.put(0, "hello");
    map2.put(1, "hi");
    map2.put("greeting", 2);

    assertTrue(tester.compareMaps(map1, map2));

    map1.put(6, "wrong value");

    assertFalse(tester.compareMaps(map1, map2));

  }
}