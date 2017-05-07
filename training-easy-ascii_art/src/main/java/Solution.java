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
		List<String> output = bot.find_solution(model);
		game.solve(output);
	}
}

class Model {
	String text;

	int letterWidth;
	int letterHeight;
	String asciiLetters;

	static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ? ";
	static int ALPHABET_COUNT = 27;
}

class Bot {

	List<String> find_solution(Model model) {
		List<String> rows = new ArrayList<>();
		for (int iRow = 0; iRow < model.letterHeight; iRow++) {
			String row = "";
			for (int iText = 0; iText < model.text.length(); iText++) {
				char letter = model.text.charAt(iText);
				int iLetter = Data.get_index_from_letter(letter);
				String letterRow = Data.get_letter_row(model, iLetter, iRow);
				row += letterRow;
			}
			rows.add(row);
		}
		return rows;
	}
}

class Data {

	static int get_index_from_letter(char chaz) {
		String upperChar = String.valueOf(chaz).toUpperCase();
		int iLetter = Model.ALPHABET.indexOf(upperChar);
		return iLetter;
	}

	static String get_letter_row(Model model, int iLetter, int iLetterRow) {
		int rowWidth = (Model.ALPHABET_COUNT * model.letterWidth);
		int beg = (iLetterRow * rowWidth) + (iLetter * model.letterWidth);
		int end = (beg + model.letterWidth);
		String letterRow = model.asciiLetters.substring(beg, end);
		return letterRow;
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
		for (int iLetter = 0; iLetter < Model.ALPHABET_COUNT; iLetter++) {
			Log.debug("letter[%d]", iLetter);
			for (int iLetterRow = 0; iLetterRow < model.letterHeight; iLetterRow++) {
				Log.debug("%s", Data.get_letter_row(model, iLetter, iLetterRow));
			}
		}
		return model;
	}

	void solve(List<String> outputLines) {
		for (String line : outputLines) {
			System.out.println(line);
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
