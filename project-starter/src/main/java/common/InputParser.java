package common;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {

  public static List<String> parseLines(Scanner in, int size) {
    List<String> lines = new ArrayList<>();
    Log.info("%d", size);
    for (int i = 0; i < size; i++) {
      String line = in.next();
      lines.add(line);
      Log.info("%s", line);
    }
    return lines;
  }
}