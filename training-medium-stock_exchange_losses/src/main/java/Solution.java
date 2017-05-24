import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Env.read(args);

		Scanner in = new Scanner(System.in);
		Model model = Game.init(in);
		int[] vs = model.stockValues;
		int v_loss_max = 0;
		for (int i = 0; i < vs.length; i++) {
			int v0 = vs[i];
			Log.debug("for %d", v0);
			for (int y = i + 1; y < vs.length; y++) {
				int v1 = vs[y];
				int v0v1_loss = v1 - v0;
				Log.debug("  with %d diff=%d", v1, v0v1_loss);
				if (v0v1_loss < v_loss_max)
					v_loss_max = v0v1_loss;
			}
		}

		System.out.println(v_loss_max);
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
			m.stockValues[i] = v;
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

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}