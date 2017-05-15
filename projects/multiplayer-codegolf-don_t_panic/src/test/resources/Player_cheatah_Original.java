import java.util.*;

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int nbFloors = in.nextInt(); // number of floors
        int width = in.nextInt(); // width of the area
        int nbRounds = in.nextInt(); // maximum number of rounds
        int exitFloor = in.nextInt(); // floor on which the exit is found
        int exitPos = in.nextInt(); // position of the exit on its floor
        int nbTotalClones = in.nextInt(); // number of generated clones
        int nbAdditionalElevators = in.nextInt(); // ignore (always zero)
        int nbElevators = in.nextInt(); // number of elevators
        HashMap<Integer, Integer> elevators = new HashMap<Integer, Integer>(nbElevators);
        elevators.put(exitFloor, exitPos);
        for (int i = 0; i < nbElevators; i++) {
            elevators.put(in.nextInt(), in.nextInt());
        }

        // game loop
        while (true) {
            int cloneFloor = in.nextInt(); // floor of the leading clone
            int clonePos = in.nextInt(); // position of the leading clone on its floor
            String dir = in.next(); // direction of the leading clone: LEFT or RIGHT
            
            if ((dir.equals("LEFT") && elevators.get(cloneFloor) > clonePos) || (dir.equals("RIGHT") && cloneFloor >= 0 && elevators.get(cloneFloor) < clonePos)) {
                System.out.println("BLOCK");
            } else {
                System.out.println("WAIT"); // action: WAIT or BLOCK
            }
        }
    }
}