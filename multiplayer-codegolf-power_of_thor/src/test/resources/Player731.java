import java.util.Scanner;

class Player {

  public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lightX = in.nextInt();
        int lightY = in.nextInt();
        int initialTX = in.nextInt();
        int initialTY = in.nextInt();
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
