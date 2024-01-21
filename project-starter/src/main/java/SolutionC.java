import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class SolutionC {

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    String s = in.nextLine();

    // Write an answer using System.out.println()
    // To debug: System.err.println("Debug messages...");

    char[] chars = s.toCharArray();
    for (int i = 0; i < 7; i++) {
      var line = "";
      for (char ch : chars) {
        var binChar = removeLeadZeros(asciiToBinary((ch + "")));
         line += binChar.substring(i, i+1);
      }
      System.out.println(line);
    }
  }

  static String removeLeadZeros(String binString) {
    return binString.replaceFirst("^0+(?!$)", "");
  }


  static String asciiToBinary(String asciiString) {
    byte[] bytes = asciiString.getBytes();
    StringBuilder binary = new StringBuilder();
    for (byte b : bytes) {
      int val = b;
      for (int i = 0; i < 8; i++) {
        binary.append((val & 128) == 0 ? 0 : 1);
        val <<= 1;
      }
      // binary.append(' ');
    }
    return binary.toString();
  }
}
