import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Env.read(args);

		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		Log.info("%d", n);

		int p = 0, p_tmp;
		int i, j;
		int[] v_array = new int[n];

		for (i = 0; i < n; i++) {
			v_array[i] = in.nextInt();
			for (j = 0; j < i; j++) {
				p_tmp = v_array[i] - v_array[j];
				if (p_tmp < p)
					p = p_tmp;
			}
		}

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
