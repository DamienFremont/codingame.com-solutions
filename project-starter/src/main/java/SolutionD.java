import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Character.toLowerCase;
import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toCollection;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class SolutionD {

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    String s = in.nextLine();

    // Write an answer using System.out.println()
    // To debug: System.err.println("Debug messages...");

    var chars = toChars(s);
    var numbers = chars
            .stream()
            .map(c -> toInt(c))
            .collect(toCollection(ArrayList::new));

    var biggest = numbers
            .stream()
            .max(comparingInt(a -> a)).get();
    var lowest = numbers
            .stream()
            .min(comparingInt(a -> a)).get();

    System.out.println(lowest);
    System.out.println(biggest);
  }

  static int toInt(char ch) {
    return parseInt(String.valueOf(ch));
  }

  static List<Character> toChars(String str) {
    List<Character> chars = new ArrayList<>();
    for (char ch : str.toCharArray()) {
      chars.add(toLowerCase(ch));
    }
    return chars;
  }
}
