import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Env.read(args);

		Scanner in = new Scanner(System.in);
		Model model = Game.init(in);

		System.out.println("answer");
	}
}

class Model {
	int[] stockValues;
}

class Game {
	static Model init(Scanner in) {
		Log.debug("INIT =======================");
		Model m = new Model();
		int n = in.nextInt();
		Log.info("%d", n);
		m.stockValues = new int[n];
		String vs = "";
		for (int i = 0; i < n; i++) {
			int v = in.nextInt();
			m.stockValues[i] = n;
			vs += v + " ";
		}

		Log.info("%s", vs);
		return m;
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

class Point {
	int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Vector {
	double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
}

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}