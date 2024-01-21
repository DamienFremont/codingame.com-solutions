import common.Env;
import common.Log;

import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class SolutionB {

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    String input = in.nextLine();

    // Write an answer using System.out.println()
    // To debug: System.err.println("Debug messages...");

    Env.read(args);

    var width = Integer.valueOf(input.split("\\(")[1].split(",")[0]);
    var height = Integer.valueOf(input.split(",")[1].split("\\)")[0]);
    var stairs = Integer.valueOf(input.split("\\)")[1].trim());

    var stairW = width / stairs;
    var stairH = height / stairs;

    int lastLineWidth = 0;
    for (int currStair = 0; currStair < stairs; currStair++) {

      var lineIndent = "";
      for (int i = 0; i < lastLineWidth; i++) {
        lineIndent += " ";
      }

      // stairTop
      var line = lineIndent;
      if(currStair > 0) {
        line += "+";
      }
      for (int s = 0; s <= stairW; s++) {
        line = line + "-";
      }
      lastLineWidth = line.length() -1;
      System.out.println(line);
      Log.debug(line);

      // stairWalls
      var lineIndent2 = "";
      for (int i = 0; i < lastLineWidth; i++) {
        lineIndent2 += " ";
      }
      var stairWall = lineIndent2 + ":";
      for (int i = 0; i < stairH; i++) {
        System.out.println(stairWall);
        Log.debug(stairWall);
      }
    }
  }
}
