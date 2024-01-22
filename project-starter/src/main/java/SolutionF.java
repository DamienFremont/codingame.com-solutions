import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class SolutionF {

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);

    int people = 1; // me

    int n = in.nextInt();
    for (int i = 0; i < n; i++) {
      int getIn = in.nextInt();
      int getOff = in.nextInt();

      people += getIn - getOff;
    }

    // Write an answer using System.out.println()
    // To debug: System.err.println("Debug messages...");

    System.out.println(people);
  }

}
