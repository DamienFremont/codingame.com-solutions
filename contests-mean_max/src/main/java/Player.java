import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			int myScore = in.nextInt();
			int enemyScore1 = in.nextInt();
			int enemyScore2 = in.nextInt();
			int myRage = in.nextInt();
			int enemyRage1 = in.nextInt();
			int enemyRage2 = in.nextInt();
			int unitCount = in.nextInt();
			for (int i = 0; i < unitCount; i++) {
				int unitId = in.nextInt();
				int unitType = in.nextInt();
				int player = in.nextInt();
				float mass = in.nextFloat();
				int radius = in.nextInt();
				int x = in.nextInt();
				int y = in.nextInt();
				int vx = in.nextInt();
				int vy = in.nextInt();
				int extra = in.nextInt();
				int extra2 = in.nextInt();
			}

			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			System.out.println("WAIT");
			System.out.println("WAIT");
			System.out.println("WAIT");
		}
	}
}

// MODEL

class AreaModel {
	int radius = 6000;
}

class UnitModel {
	int unitId;
	UnitType unitType;
	int playerId;
	int mass;
	int radius;
	/** The position of the unit */
	int x, y;
	/** The speed of the unit */
	int vx, vy;
	/**  */
	int extra;
	int extra2;
}

enum UnitType {
	REAPER, A, B, C, WRECK;
}

// UTIL

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}

class Log {
	
	static boolean Level_DEBUG, Level_INFO;

	static void info(String pattern, Object... values) {
		if (!Level_DEBUG)
			return;
		log(pattern, values);
	}

	static void debug(String pattern, Object... values) {
		if (!Level_DEBUG)
			return;
		log(pattern, values);
	}

	private static void log(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}

class Env {

	static void read(String args[]) {
		Log.Level_DEBUG = false;
		Log.Level_INFO = false;
		if (args == null)
			return;
		for (String arg : args) {
			if ("-debug".equals(arg))
				Log.Level_DEBUG = true;
			if ("-info".equals(arg))
				Log.Level_INFO = true;
		}
	}
}