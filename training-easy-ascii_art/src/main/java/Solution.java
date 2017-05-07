import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		Game game = new Game();
		Model model = new Model();
		Bot bot = new Bot();

		model = game.init(in, model);
		String output = bot.find_solution(model);
		game.solve(output);
	}
}

class Model {
	String text;

	int letterWidth;
	int letterHeight;
	String asciiLetters;

	int alphabetCount = 26;
}

class Bot {

	String find_solution(Model model) {
		return "#";
	}
}

class Data {
	static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	static char char_from_index(int index) {
		return alphabet.charAt(index);
	}

	static int index_from_char(char chaz) {
		return alphabet.indexOf(String.valueOf(chaz).toUpperCase());
	}

	static String get_char_row(int index) {
//		for (int iLetter = 0; iLetter < model.alphabetCount; iLetter++) {
//			String letterRows = model.chars.get(iLetter);
//			Log.debug("letter %d(%s)=%s", iLetter, Data.char_from_index(iLetter), letterRows);
//			for (int iRow = 0; iRow < H; iRow++) {
//				int beg = iLetter + iRow * L;
//				int end = iLetter + L + iRow * L;
//				Log.debug(letterRows.substring(beg, end));
//			}
		return " ";
	}
}

class Game {

	Model init(Scanner in, Model model) {
		model.letterWidth = in.nextInt();
		model.letterHeight = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		model.text = in.nextLine();
		model.asciiLetters = "";
		for (int iRow = 0; iRow < model.letterHeight; iRow++) {
			String row = in.nextLine();
			model.asciiLetters += row;
		}
		Log.debug("L=%d, H=%d, T=%s", model.letterWidth, model.letterHeight, model.text);
		Log.debug("ASCII=%s", model.asciiLetters);
		return model;
	}

	void solve(String output) {
		System.out.println(output);
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
