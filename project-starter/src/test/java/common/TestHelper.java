package common;

import java.io.File;
import java.nio.file.Files;

import static java.lang.String.format;

public class TestHelper {

  public static String inputFile(String id) {
    return loadResource(format("%s-input.txt", id));
  }
  public static String outputFile(String id) {
    return loadResource(format("%s-output.txt", id));
  }

  static String loadResource(String resourceName) {
    try {
      var classLoader = ClassLoader.getSystemClassLoader();
      var file = new File(classLoader.getResource(resourceName).getFile());
      return new String(Files.readAllBytes(file.toPath()));
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }


}
