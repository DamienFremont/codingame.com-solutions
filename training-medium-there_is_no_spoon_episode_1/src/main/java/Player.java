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
		List<String> res = new ArrayList<>();
		for (int y = 0; y < model.height; y++) {
			for (int x = 0; x < model.width; x++) {
				if (isNodeExist(model, y, x)) {
					int xRight = -1, yRight = -1;
					if (isNodeExist(model, y, x + 1)) {
						xRight = x + 1;
						yRight = y;
					}
					int xBottm = -1, yBottm = -1;
					if (isNodeExist(model, y + 1, x)) {
						xBottm = x;
						yBottm = y + 1;
					}
					res.add(String.format("%d %d %d %d %d %d", //
							x, y, //
							xRight, yRight, //
							xBottm, yBottm));
				}
			}
		}
		return res;
	}

	private static boolean isNodeExist(Model model, int y, int x) {
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
		for (int i = 0; i < model.height; i++) {
			String line = "";
			for (int j = 0; j < model.width; j++) {
				line += model.grid[i][j] ? "0" : ".";
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