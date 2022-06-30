package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

  /**
   * Top level search workflow
   *
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under the rootDir
   */
  List<File> listFiles(String rootDir);

  /**
   * Return a file and return all the lines
   * <p>
   * FileReader: Opens a file to be read as an input stream. Assumes default buffer and character
   * encoding BufferedReader: Reads an input stream and buffers the input in order to increase
   * efficiency Character Encoding: How bytes are converted to characters that are human readable.
   * Most used encoding today is UTF-8.
   *
   * @param inputFile file to read
   * @return lines
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  List<String> readLines(File inputFile);

  /**
   * check if a line contains the regex pattern (passed by user)
   *
   * @param line input string
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   * <p>
   * FileOutputStream: Opens a stream to write data to a file. OutputStreamWriter: Is a stream that
   * converts character streams to byte stream using a charset. BufferedWriter: Writes text to a
   * character stream. Buffered to increase efficiency.
   *
   * @param lines matched line
   * @throws IOException if write failed
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);
}
