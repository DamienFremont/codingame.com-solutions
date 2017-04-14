import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {

	static class Point {
		int x;
		int y;
	}

	static Point myPod = new Point();
	static Point opponentPod = new Point();
	static Point nextCheckpoint = new Point();

	public static void main(String args[]) {
		try (Scanner in = new Scanner(System.in)) {

			// game loop
			while (true) {
				myPod.x = in.nextInt();
				myPod.y = in.nextInt();
				nextCheckpoint.x = in.nextInt();
				nextCheckpoint.y = in.nextInt();
				int nextCheckpointDistance = in.nextInt();
				int nextCheckpointAngleBetweenPodNextCheckPoint = in.nextInt();
				opponentPod.x = in.nextInt();
				opponentPod.y = in.nextInt();

				// Write an action using System.out.println()
				// To debug: System.err.println("Debug messages...");

				int meNewX = nextCheckpoint.x;
				int meNewY = nextCheckpoint.y;
				int meNewThrust = compute_thrust(nextCheckpointAngleBetweenPodNextCheckPoint);

				System.err.println("Debug messages... meNewX=" + meNewX);
				System.err.println("Debug messages... meNewY=" + meNewY);
				System.err.println("Debug messages... meNewThrust=" + meNewThrust);
				output_X_Y_T(meNewX, meNewY, meNewThrust);
			}
		}
	}

	private static int compute_thrust(int nextCheckpointAngleBetweenPodNextCheckPoint) {
		int thrust = 0;
		if ((nextCheckpointAngleBetweenPodNextCheckPoint > 90) || (nextCheckpointAngleBetweenPodNextCheckPoint < -90)) {
			thrust = 0;
		} else {
			thrust = 100;
		}
		return thrust;
	}

	static void output_X_Y_T(int x, int y, int thrust) {
		Preconditions.check(0 <= thrust);
		Preconditions.check(thrust <= 100);
		System.out.println(x + " " + y + " " + thrust);
	}

	static class Preconditions {
		static void check(boolean condition) {
			if (!condition)
				throw new RuntimeException("CONDITION FALSE");
		}
	}

}