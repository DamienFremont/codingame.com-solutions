import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Env.read(args);

		Log.info("INIT =======================");
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		Log.info("%d", n);
		int p, v, vMin, vMax;

		Log.info("SOLVE =======================");
		p = 0;
		v = vMin = vMax = in.nextInt();
		Log.debug("for %d p=%d v[%d..%d]", v, p, vMin, vMax);
		for (int i = 1; i < n; i++) {
			v = in.nextInt();
			if (v > vMax) {
				vMin = vMax = v;
			} else if (v < vMin) {
				vMin = v;
				int loss = vMin - vMax;
				if (loss < p)
					p = loss;
			}
			Log.debug("for %d p=%d v[%d..%d]", v, p, vMin, vMax);
		}

		Log.info("ANSWER =======================");
		System.out.println(p);
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
