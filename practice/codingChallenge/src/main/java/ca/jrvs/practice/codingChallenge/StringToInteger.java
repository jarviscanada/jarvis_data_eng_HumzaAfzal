package ca.jrvs.practice.codingChallenge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://www.notion.so/jarvisdev/String-to-Integer-atoi-4a8ba66bb42346238ebd2c597c98a6d5
 */
public class StringToInteger {

  public int solution1(String s) {
    String regex = "^ *";
    Pattern pattern = Pattern.compile(regex);

    Matcher matcher = pattern.matcher(s);
    if (matcher.find()) {
      String matched = matcher.replaceAll("");

      regex = "^[+-]?\\d+";
      pattern = Pattern.compile(regex);
      matcher = pattern.matcher(matched);
      if (matcher.find()) {
        try {
          int number = Integer.parseInt(matcher.group(0));
          return number;
        } catch (NumberFormatException e) {
          if (matcher.group(0).charAt(0) == '-') {
            return -2147483648;
          } else {
            return 2147483647;
          }
        }

      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  public int solution2(String s) {

    int index = 0;
    int negated = 1;
    int value = 0;
    if (s.length() == 0) {
      return value;
    }
    while (index < s.length() && s.charAt(index) == ' ') {
      index++;
    }
    if (index == s.length()) {
      return value;
    }
    if (s.charAt(index) == '-') {
      index++;
      negated = -1;
    } else if (s.charAt(index) == '+') {
      index++;
    }
    while (index < s.length() && s.charAt(index) == '0') {
      index++;
    }
    while (index < s.length() && s.charAt(index) >= '0' && s.charAt(index) <= '9') {

      if (Integer.MAX_VALUE / 10 < value || (value == Integer.MAX_VALUE / 10
          && s.charAt(index) - '0' > 7)) {
        if (negated == -1) {
          return Integer.MIN_VALUE;
        } else {
          return Integer.MAX_VALUE;
        }
      }
      value = 10 * value + (s.charAt(index) - '0');

      index++;
    }
    return negated * value;
  }
}