package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepLambdaImp extends JavaGrepImp {

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);
    try {
      javaGrepLambdaImp.process();
    } catch (Exception e) {
      javaGrepLambdaImp.logger.error("Error: Unable to process", e);
    }
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<String>();
    try {
      Files.lines(inputFile.toPath()).forEach(line -> lines.add(line));
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Not a File");
    }
    return lines;
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<File>();
    try {
      Stream<Path> pathStream = Files.list(Paths.get(System.getProperty("user.dir"), rootDir));

      Stream<File> stream = pathStream.map(Path::toFile);
      stream.filter(file -> !file.isDirectory()).forEach(files::add);
      stream.filter(File::isDirectory).map(file -> listFiles(file.toString())).forEach(file -> files.add((File) file));
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Not a File");
    }
    return files;
  }
}
