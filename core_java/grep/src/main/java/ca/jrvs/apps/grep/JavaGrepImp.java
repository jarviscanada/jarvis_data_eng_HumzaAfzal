package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);
    try {
      javaGrepImp.process();
    } catch (Exception e) {
      javaGrepImp.logger.error("Error: Unable to process", e);
    }
  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    List<File> files = listFiles(this.rootPath);
    List<String> lines;
    File file;
    String line;
    try {
      for (int i = 0; i < files.size(); i++) {

        file = files.get(i);
        lines = readLines(file);

        for (int j = 0; j < lines.size(); j++) {

          line = lines.get(j);

          if (containsPattern(line)) {

            matchedLines.add(line);
          }
        }
      }
    } catch (Exception e) {
      throw e;
    }

    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {

    File dir = new File(rootDir);
    List<File> files = Arrays.asList(dir.listFiles());
    List<File> cleanedFiles = new ArrayList<File>();

    for (int i = 0; i < files.size(); i++) {
      if (files.get(i).isDirectory()) {
        cleanedFiles.addAll(listFiles(files.get(i).toString()));
      } else {
        cleanedFiles.add(files.get(i));
      }
    }

    return cleanedFiles;
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = null;
    try {
      FileReader reader = new FileReader(inputFile);
      BufferedReader bufferedReader = new BufferedReader(reader);
      lines = bufferedReader.lines().collect(Collectors.toList());
      bufferedReader.close();
      reader.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Not a File");
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {

    return line.matches(this.regex);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try {
      OutputStream stream = new FileOutputStream(this.outFile);
      OutputStreamWriter writer = new OutputStreamWriter(stream);
      BufferedWriter bufferedWriter = new BufferedWriter(writer);
      for (int i = 0; i < lines.size(); i++) {
        bufferedWriter.write(lines.get(i));
        bufferedWriter.write("\n");
      }
      bufferedWriter.close();
      writer.close();
      stream.close();
    } catch (IOException e) {
      throw e;
    }
  }

  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return this.regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return this.outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
