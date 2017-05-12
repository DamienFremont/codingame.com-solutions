import java.util.Scanner;

import javax.management.RuntimeErrorException;

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
	Point startPos;
	Direction bombDir;
	Point nextJump;

	static enum Direction {
		U, UR, R, DR, D, DL, L, UL
	}

}

class Bot {
	static Model solve(Model model) {
		Log.debug("SOLVE =======================");
		model.nextJump = model.nextJump == null ? model.startPos : model.nextJump;
		if (model.bombDir.toString().contains("U"))
			model.nextJump.y--;
		if (model.bombDir.toString().contains("D"))
			model.nextJump.y++;
		if (model.bombDir.toString().contains("L"))
			model.nextJump.x--;
		if (model.bombDir.toString().contains("R"))
			model.nextJump.x++;
		Log.debug("SOLUTION: nextJump=%d,%d", model.nextJump.x, model.nextJump.y);
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
		model.startPos = new Point();
		model.startPos.x = in.nextInt();
		model.startPos.y = in.nextInt();
		Log.debug("%d %d", model.W, model.H);
		Log.debug("%d", model.N);
		Log.debug("%d %d", model.startPos.x, model.startPos.y);
		return model;
	}

	static Model turn(Scanner in, Model model) {
		Log.debug("TURN =======================");
		model.bombDir = Model.Direction.valueOf(in.next());
		Log.debug("%s", model.bombDir);
		return model;
	}

	static void play(Model model) {
		System.out.println(String.format("%d %d", model.nextJump.x, model.nextJump.y));
	}
}

// UTILS

class Log {
	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}

class Point {
	int x, y;
}
