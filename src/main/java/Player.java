import java.util.*;
import java.io.*;
import java.math.*;

class Player {

	public static void main(String args[]) {

		// OBJECTS
		Point playerPod = new Point();
		Point opponentPod = new Point();
		Point nextCheckpoint = new Point();
		GameState gameState = new GameState();

		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			playerPod.x = in.nextInt();
			playerPod.y = in.nextInt();
			nextCheckpoint.x = in.nextInt();
			nextCheckpoint.y = in.nextInt();
			gameState.nextCheckpointDistance = in.nextInt();
			gameState.nextCheckpointAngle = in.nextInt();
			opponentPod.x = in.nextInt();
			opponentPod.y = in.nextInt();

			gameState.turn++;

			Move nextMove = compute(nextCheckpoint, gameState);

			debug(gameState, nextMove);
			play(nextMove);
		}
	}

	// FUNCTIONS UTILS

	static void debug(String str) {
		System.err.println(str);
	}

	static class Preconditions {
		static void check(boolean condition) {
			if (!condition)
				throw new RuntimeException("CONDITION FALSE");
		}
	}

	static void debug(GameState gameState, Move nextMove) {
		debug("gameState.turn=" + gameState.turn);
		debug("gameState.isBoostUsed=" + gameState.isBoostUsed);

		debug("gameState.nextCheckpointDistance=" + gameState.nextCheckpointDistance);
		debug("gameState.nextCheckpointAngle=" + gameState.nextCheckpointAngle);

		debug("nextMove.x=" + nextMove.x);
		debug("nextMove.y=" + nextMove.x);
		debug("nextMove.thrust=" + nextMove.thrust);
		debug("nextMove.useBoost=" + nextMove.useBoost);
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

	static Move compute(Point nextCheckpoint, GameState gameState) {
		Move nextMove = new Move();
		nextMove.x = nextCheckpoint.x;
		nextMove.y = nextCheckpoint.y;
		nextMove.thrust = compute_thrust(gameState);
		nextMove.useBoost = isOkToBoost(gameState);
		return nextMove;
	}

	static int compute_thrust(GameState gameState) {
		int thrust = 0;
		boolean isReversedRight = gameState.nextCheckpointAngle > 90;
		boolean isReversedLeft = gameState.nextCheckpointAngle < -90;
		boolean isReversed = isReversedRight || isReversedLeft;
		boolean isAproachingTooFast = gameState.nextCheckpointDistance < 1000;
		if (isReversed) {
			thrust = 0;
		} else if (isAproachingTooFast) {
			thrust = 20;
		} else {
			thrust = 100;
		}
		return thrust;
	}

	static boolean isOkToBoost(GameState gameState) {
		if (gameState.isBoostUsed)
			return false;
		boolean isFacingIt = (gameState.nextCheckpointAngle == 0);
		boolean isDistanceEnough = (gameState.nextCheckpointDistance > 7000);
		boolean isOkToBoost = isFacingIt && isDistanceEnough;
		if (gameState.isBoostUsed)
			gameState.isBoostUsed = true;
		return isOkToBoost;
	}

	// CLASSES

	static class Point {
		int x;
		int y;
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

		int nextCheckpointDistance;
		int nextCheckpointAngle;
	}
}