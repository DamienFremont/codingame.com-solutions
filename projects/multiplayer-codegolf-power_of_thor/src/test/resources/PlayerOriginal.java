import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * ---
 * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lightX = in.nextInt(); // the X position of the light of power
        int lightY = in.nextInt(); // the Y position of the light of power
        int initialTX = in.nextInt(); // Thor's starting X position
        int initialTY = in.nextInt(); // Thor's starting Y position

        // game loop
        while (true) {
            int remainingTurn = in.nextInt();

            String path = "";
            if ( initialTY < lightY ) { path += "S"; initialTY++; };
            if ( initialTY > lightY ) { path += "N"; initialTY--; };
            if ( initialTX < lightX ) { path += "E"; initialTX++; };
            if ( initialTX > lightX ) { path += "W"; initialTX--; };
            System.out.println( path );
        }
    }
}