package ca.jrvs.apps.practice;

public interface RegexExc {

  /**
   * Check if filename end with a .jpg or a .jpeg
   * @param filename name of file
   * @return return true if filename extension is jpeg or jpeg (case insensitive)
   */
  public boolean matchJpeg(String filename);

  /**
   * Check if IP address is within the ranges 0.0.0.0 to 999.999.999.999
   * @param ip ip address
   * @return true if ip is valid
   */
  public boolean matchIp(String ip);

  /**
   * Check if this line contains any text
   * @param line
   * @return return true if line is empty or white space
   */
  public boolean isEmptyLine(String line);

}
