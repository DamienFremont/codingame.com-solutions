import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Env.read(args);

		Log.info("INIT =======================");
		Scanner in = new Scanner(System.in);
		int W = in.nextInt(), H = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		Log.info("%d %d", W, H);
		int[][] rooms = rooms(in, W, H);
		int EX = in.nextInt();
		Log.info("%d", EX);

		while (in.hasNext()) {

			Log.info("TURN =======================");
			int XI = in.nextInt(), YI = in.nextInt();
			String POS = in.next();
			Log.info("%d %d %s", XI, YI, POS);

			Log.info("SOLVE =======================");
			int roomType = rooms[YI][XI];
			Log.debug("roomType=%d", roomType);
			String exit = exit(POS, roomType);
			Log.debug("exit=%s", exit);
			int X = nextX(exit, XI);
			int Y = nextY(exit, YI);
			Log.debug("X Y=%d %d", XI, YI);

			Log.info("PLAY =======================");
			System.out.println(String.format("%d %d", X, Y));
		}
	}

	static final String[][][] ROOM_TYPES = { //
			{ {} }, // 0
			{ { "TOP", "BOT" }, { "LEFT", "BOT" }, { "RIGHT", "BOT" } }, // 1
			{ { "LEFT", "RIGHT" }, { "RIGHT", "LEFT" } }, // 2
			{ { "TOP", "BOT" } }, // 3
			{ { "TOP", "LEFT" }, { "RIGHT", "BOT" } }, // 4
			{ { "TOP", "RIGHT" }, { "LEFT", "BOT" } }, // 5
			{ { "LEFT", "RIGHT" }, { "RIGHT", "LEFT" } }, // 6
			{ { "TOP", "BOT" }, { "RIGHT", "BOT" } }, // 7
			{ { "LEFT", "BOT" }, { "RIGHT", "BOT" } }, // 8
			{ { "LEFT", "BOT" }, { "TOP", "BOT" } }, // 9
			{ { "TOP", "LEFT" } }, // 10
			{ { "TOP", "RIGHT" } }, // 11
			{ { "RIGHT", "BOT" } }, // 12
			{ { "LEFT", "BOT" } } // 13
	};

	static int[][] rooms(Scanner in, int W, int H) {
		int[][] rooms = new int[H][W];
		for (int y = 0; y < H; y++) {
			rooms[y] = new int[W];
			String LINE = in.nextLine();
			for (int x = 0; x < W; x++) {
				rooms[y][x] = Integer.valueOf(LINE.split(" ")[x]);
			}
			Log.info("%s", LINE);
		}
		return rooms;
	}

	static String exit(String entrance, int roomType) {
		for (String[] paths : ROOM_TYPES[roomType]) {
			if (entrance.equals(paths[0]))
				return paths[1];
		}
		throw new RuntimeException();
	}

	static int nextY(String exit, int YI) {
		if (exit.equals("BOT")) {
			return YI + 1;
		}
		return YI;
	}

	static int nextX(String exit, int XI) {
		if (exit.equals("LEFT")) {
			XI--;
		} else if (exit.equals("RIGHT")) {
			XI++;
		}
		return XI;
	}
}

// UTILS

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