import java.util.*;
import java.io.*;
import java.math.*;

class Player {

	public static void main(String args[]) {

		// OBJECTS
		Point playerPod;
		Point opponentPod;
		Point nextCheckpoint;
		GameState gameState = new GameState();

		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			playerPod = new Point(in.nextInt(), in.nextInt());
			nextCheckpoint = new Point(in.nextInt(), in.nextInt());
			int nextCheckpointDistance = in.nextInt();
			int nextCheckpointAngle = in.nextInt();
			opponentPod = new Point(in.nextInt(), in.nextInt());

			gameState.turn++;

			Move nextMove = new Move();
			nextMove.x = nextCheckpoint.x;
			nextMove.y = nextCheckpoint.y;
			nextMove.thrust = compute_thrust(nextCheckpointAngle);
			nextMove.useBoost = isOkToBoost(gameState, nextCheckpointDistance, nextCheckpointAngle);

			debug("nextCheckpointDistance=" + nextCheckpointDistance);
			debug("nextCheckpointAngle=" + nextCheckpointAngle);

			debug("gameState.turn=" + gameState.turn);
			debug("gameState.isBoostUsed=" + gameState.isBoostUsed);

			debug("nextMove.x=" + nextMove.x);
			debug("nextMove.y=" + nextMove.x);
			debug("nextMove.thrust=" + nextMove.thrust);
			debug("nextMove.useBoost=" + nextMove.useBoost);

			play(nextMove);
		}
	}

	// FUNCTIONS UTILS

	private static void debug(String str) {
		System.err.println(str);
	}

	static class Preconditions {
		static void check(boolean condition) {
			if (!condition)
				throw new RuntimeException("CONDITION FALSE");
		}
	}

	// FUNCTIONS BOT

	static void play(Move move) {
		if (move.useBoost)
			System.out.println(move.x + " " + move.y + " BOOST");
		else {
			Preconditions.check(0 <= move.thrust);
			Preconditions.check(move.thrust <= 100);
			System.out.println(move.x + " " + move.y + " " + move.thrust);
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

	static boolean isOkToBoost(GameState gameState, int distance, int angle) {
		if (gameState.isBoostUsed)
			return false;
		gameState.isBoostUsed = true;
		boolean isFacingIt = (angle == 0);
		boolean isDistanceEnough = (distance > 7000);
		return isFacingIt && isDistanceEnough;
	}

	// CLASSES

	static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class Move {
		int x;
		int y;
		int thrust;
		boolean useBoost;
	}

	static class GameState {
		int turn = 0;
		boolean isBoostUsed = false;
	}
}