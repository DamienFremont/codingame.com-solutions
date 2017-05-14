import java.util.Scanner;

class Player {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Model model = Game.init(in);
		while (in.hasNext()) {
			model = Game.turn(in, model);
			model = Bot.solve(model);
			Game.play(model);
		}
	}
}

class Model {
	int W, H;
	int N;
	int X0, Y0;

	String bombDir;
	int X, Y;

	int X_MIN, X_MAX, Y_MIN, Y_MAX;
}

class Bot {
	static Model solve(Model model) {
		Log.debug("SOLVE =======================");
		Log.debug("AREA: X[%d,%d],Y[%d,%d]", model.X_MIN, model.X_MAX, model.Y_MIN, model.Y_MAX);
		if (model.bombDir.contains("U")) {
			model.Y_MAX = model.Y - 1;
			int marge = Math.abs((model.Y_MAX - model.Y_MIN) / 2);
			model.Y = model.Y_MAX - marge;
		} else if (model.bombDir.contains("D")) {
			model.Y_MIN = model.Y + 1;
			int marge = Math.abs((model.Y_MAX - model.Y_MIN) / 2);
			model.Y = model.Y_MIN + marge;
		}
		if (model.bombDir.contains("L")) {
			model.X_MAX = model.X - 1;
			int marge = Math.abs((model.X_MAX - model.X_MIN) / 2);
			model.X = model.X_MAX - marge;
		} else if (model.bombDir.contains("R")) {
			model.X_MIN = model.X + 1;
			int marge = Math.abs((model.X_MAX - model.X_MIN) / 2);
			model.X = model.X_MIN + marge;
		}
		Log.debug("SOLUTION: nextJump=%d,%d", model.X, model.Y);
		return model;
	}
}

class Game {
	static Model init(Scanner in) {
		Log.debug("INIT =======================");
		Model model = new Model();
		model.W = in.nextInt();
		model.H = in.nextInt();
		model.N = in.nextInt();
		model.X0 = model.X = in.nextInt();
		model.Y0 = model.Y = in.nextInt();
		Log.debug("%d %d", model.W, model.H);
		Log.debug("%d", model.N);
		Log.debug("%d %d", model.X0, model.Y0);
		model.X_MIN = model.Y_MIN = 0;
		model.X_MAX = model.W - 1;
		model.Y_MAX = model.H - 1;
		return model;
	}

	static Model turn(Scanner in, Model model) {
		Log.debug("TURN =======================");
		model.bombDir = in.next();
		Log.debug("%s", model.bombDir);
		return model;
	}

	static void play(Model model) {
		System.out.println(String.format("%d %d", model.X, model.Y));
	}
}

// UTILS

class Log {
	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}
