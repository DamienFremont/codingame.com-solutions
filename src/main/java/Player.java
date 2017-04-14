import java.util.*;
import java.io.*;
import java.math.*;

class Player {

	static class Point {
		int x;
		int y;
	}

	static Point playerPod = new Point();
	static Point opponentPod = new Point();
	
	static Point nextCheckpoint = new Point();
	
	static boolean isBoostUsed = false;

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			playerPod.x = in.nextInt();
			playerPod.y = in.nextInt();
			nextCheckpoint.x = in.nextInt();
			nextCheckpoint.y = in.nextInt();
			int nextCheckpointDistance = in.nextInt();
			int nextCheckpointAngle = in.nextInt();
			opponentPod.x = in.nextInt();
			opponentPod.y = in.nextInt();

			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			int meNewX = nextCheckpoint.x;
			int meNewY = nextCheckpoint.y;
			int meNewThrust = compute_thrust(nextCheckpointAngle);

			System.err.println("Debug messages... nextCheckpointDistance=" + nextCheckpointDistance);
			System.err.println("Debug messages... nextCheckpointAngle=" + nextCheckpointAngle);

			System.err.println("Debug messages... meNewX=" + meNewX);
			System.err.println("Debug messages... meNewY=" + meNewY);
			System.err.println("Debug messages... meNewThrust=" + meNewThrust);

			output_X_Y_T(meNewX, meNewY, meNewThrust);
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
		if (isBoostUsed)
			System.out.println(x + " " + y + " " + thrust);
		else {
			System.out.println(x + " " + y + " BOOST");
			isBoostUsed = true;
		}
	}

	static class Preconditions {
		static void check(boolean condition) {
			if (!condition)
				throw new RuntimeException("CONDITION FALSE");
		}
	}

}