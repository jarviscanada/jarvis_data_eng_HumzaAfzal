package ca.jrvs.apps.practice;
import java.util.regex.*;

public class RegexExcImp implements RegexExc {

  public boolean matchJpeg(String filename) {
    return filename.matches(".+\\.(jpg|jpeg)$");
  }

  public boolean matchIp(String ip) {
    return ip.matches("[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}");
  }

  public boolean isEmptyLine(String line) {
    return line.matches("^\\s*$");
  }
}
