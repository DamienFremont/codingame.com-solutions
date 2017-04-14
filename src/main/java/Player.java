import java.util.*;
import java.io.*;
import java.math.*;

/**
 * This code automatically collects game data in an infinite loop.
 * It uses the standard input to place data into the game variables such as x and y.
 * YOU DO NOT NEED TO MODIFY THE INITIALIZATION OF THE GAME VARIABLES.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int x = in.nextInt(); // x position of your pod
            int y = in.nextInt(); // y position of your pod
            int nextCheckpointX = in.nextInt(); // x position of the next check point
            int nextCheckpointY = in.nextInt(); // y position of the next check point

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // Edit this line to output the target position
            // and thrust (0 <= thrust <= 100)
            // i.e.: "x y thrust"
            System.out.println(nextCheckpointX + " " + nextCheckpointY + " 100");


        }
    }
}