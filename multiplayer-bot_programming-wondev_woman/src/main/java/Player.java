import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Env.read(args);

		Log.info("INIT =======================");
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		int unitsPerPlayer = in.nextInt();
		Log.info("%d", size);
		Log.info("%d", unitsPerPlayer);

		String[] grid = new String[size];

		while (in.hasNext()) {
			Log.info("TURN =======================");
			for (int i = 0; i < size; i++) {
				String row = in.next();
				Log.info("%s", row);
				grid[i] = row;
			}
			for (int i = 0; i < unitsPerPlayer; i++) {
				int unitX = in.nextInt();
				int unitY = in.nextInt();
				Log.info("%d %s", unitX, unitY);
			}
			for (int i = 0; i < unitsPerPlayer; i++) {
				int otherX = in.nextInt();
				int otherY = in.nextInt();
				Log.info("%d %s", otherX, otherY);
			}
			int legalActions = in.nextInt();
			Log.info("%d", legalActions);
			List<String> actions = new ArrayList<>();
			for (int i = 0; i < legalActions; i++) {
				String atype = in.next();
				int index = in.nextInt();
				String dir1 = in.next();
				String dir2 = in.next();
				Log.info("%s %d %s %s", atype, index, dir1, dir2);
				actions.add(String.format("%s %d %s %s",  atype, index, dir1, dir2));
			}

			Log.info("SOLVE =======================");
			// TODO next move
			// TODO if legal
			String action = actions.get(0);
			
			Log.info("ANSWER =======================");
			System.out.println(action);
		}
	}

}

// UTILS

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}

class Log {
	static boolean Level_DEBUG;

	static void info(String pattern, Object... values) {
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
		if (args == null)
			return;
		for (String arg : args) {
			if ("-debug".equals(arg))
				Log.Level_DEBUG = true;
		}
	}
}