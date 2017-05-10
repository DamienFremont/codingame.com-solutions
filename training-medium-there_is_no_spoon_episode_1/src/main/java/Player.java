import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Model model = Game.read(in);
		Game.log(model);
		List<String> answers = Bot.solve(model);
		Game.play(answers);
	}

}

class Model {
	int width, height;
	boolean grid[][];
}

class Bot {

	static List<String> solve(Model model) {
		List<String> answers = new ArrayList<>();
		for (int y = 0; y < model.height; y++) {
			for (int x = 0; x < model.width; x++) {
				if (exist(model, x, y)) {
					int xRight = -1, yRight = -1;
					int xBottm = -1, yBottm = -1;
					if (-1 != right(model, x, y)) {
						xRight = right(model, x, y);
						yRight = y;
					}
					if (-1 != bottom(model, x, y)) {
						xBottm = x;
						yBottm = bottom(model, x, y);
					}
					String answer = String.format("%d %d %d %d %d %d", //
							x, y, //
							xRight, yRight, //
							xBottm, yBottm);
					answers.add(answer);
				}
			}
		}
		return answers;
	}

	static int right(Model model, int x, int y) {
		for (int xx = (x + 1); xx < model.width; xx++) {
			if (exist(model, xx, y))
				return xx;
		}
		return -1;
	}

	static int bottom(Model model, int x, int y) {
		for (int yy = (y + 1); yy < model.height; yy++) {
			if (exist(model, x, yy))
				return yy;
		}
		return -1;
	}

	static boolean exist(Model model, int x, int y) {
		return y < model.height //
				&& x < model.width //
				&& model.grid[x][y];
	}

}

class Game {

	static Model read(Scanner in) {
		int width = in.nextInt(), height = in.nextInt();
		boolean grid[][] = new boolean[width][height];
		if (in.hasNextLine()) {
			in.nextLine();
		}
		for (int y = 0; y < height; y++) {
			String line = in.nextLine();
			for (int x = 0; x < width; x++) {
				grid[x][y] = "0".equals(line.substring(x, x + 1)) ? true : false;
			}

		}
		Model model = new Model();
		model.width = width;
		model.height = height;
		model.grid = grid;
		return model;
	}

	static void log(Model model) {
		Log.debug("%d %d", model.width, model.height);
		for (int y = 0; y < model.height; y++) {
			String line = "";
			for (int x = 0; x < model.width; x++) {
				line += model.grid[x][y] ? "0" : ".";
			}
			Log.debug("%s", line);
		}
	}

	static void play(List<String> answers) {
		for (String out : answers) {
			System.out.println(out);
		}
	}

}

class Log {

	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}