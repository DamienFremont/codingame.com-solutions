import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Bot bot = new Bot();
		Game game = new Game();
		Timer timer = new Timer();
		Scanner in = new Scanner(System.in);
		while (true) {
			timer.begin();
			game.update(in);
			bot.play(game);
			timer.end();
		}
	}
}

class Bot {

	Move nextMove;

	void play(Game game) {
		nextMove = compute(game.nextCheckpoint, game);
		if (nextMove.useBoost) {
			game.isBoostUsed = true;
			System.out.println(nextMove.x + " " + nextMove.y + " BOOST");
		} else {
			Preconditions.check(0 <= nextMove.thrust);
			Preconditions.check(nextMove.thrust <= 100);
			System.out.println(nextMove.x + " " + nextMove.y + " " + nextMove.thrust);
		}
	}

	Move compute(Point nextCheckpoint, Game game) {
		Move nextMove = new Move();
		nextMove.x = nextCheckpoint.x;
		nextMove.y = nextCheckpoint.y;
		nextMove.thrust = compute_thrust(game);
		nextMove.useBoost = isOkToBoost(game);
		return nextMove;
	}

	int compute_thrust(Game game) {
		int thrust = 0;
		boolean isReversedRight = game.nextCheckpointAngle > 90;
		boolean isReversedLeft = game.nextCheckpointAngle < -90;
		boolean isReversed = isReversedRight || isReversedLeft;
		boolean isAproachingTooFast = game.nextCheckpointDistance < 1000;
		if (isReversed) {
			thrust = 0;
		} else if (isAproachingTooFast) {
			thrust = 20;
		} else {
			thrust = 100;
		}
		return thrust;
	}

	boolean isOkToBoost(Game game) {
		if (game.isBoostUsed)
			return false;
		boolean isFacingIt = (game.nextCheckpointAngle == 0);
		boolean isDistanceEnough = (game.nextCheckpointDistance > 7000);
		boolean isOkToBoost = isFacingIt && isDistanceEnough;
		return isOkToBoost;
	}

	void debug() {
		Engine.debug("nextMove.x=" + nextMove.x);
		Engine.debug("nextMove.y=" + nextMove.x);
		Engine.debug("nextMove.thrust=" + nextMove.thrust);
		Engine.debug("nextMove.useBoost=" + nextMove.useBoost);
	}
}

class Game {
	int turn = 0;
	boolean isBoostUsed = false;
	Point playerPod = new Point();
	Point opponentPod = new Point();
	Point nextCheckpoint = new Point();
	int nextCheckpointDistance;
	int nextCheckpointAngle;

	void debug() {
		Engine.debug("turn=" + turn);
		Engine.debug("isBoostUsed=" + isBoostUsed);
		Engine.debug("nextCheckpointDistance=" + nextCheckpointDistance);
		Engine.debug("nextCheckpointAngle=" + nextCheckpointAngle);
	}

	void update(Scanner in) {
		playerPod.x = in.nextInt();
		playerPod.y = in.nextInt();
		nextCheckpoint.x = in.nextInt();
		nextCheckpoint.y = in.nextInt();
		nextCheckpointDistance = in.nextInt();
		nextCheckpointAngle = in.nextInt();
		opponentPod.x = in.nextInt();
		opponentPod.y = in.nextInt();
		turn++;
		debug();
	}
}
// MODEL

class Point {
	int x;
	int y;
}

class Move {
	int x;
	int y;
	int thrust;
	boolean useBoost;
}

// UTILS

class Engine {

	public static void debug(String str) {
		System.err.println(str);
	}
}

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}

class Timer {

	long startTime;
	long stopTime;
	long elapsedTime;

	public void begin() {
		startTime = System.currentTimeMillis();
	}

	public void end() {
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		Engine.debug(String.format("elapsedTime= %d ms", elapsedTime));
	}
}