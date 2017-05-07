import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Game game = new Game();
		Model model = new Model();
		Bot bot = new Bot();
		model = game.init(in, model);
		List<String> outputs = bot.find_solution(model);
		game.solve(outputs);
	}
}

class Model {
	int n;
	int q;
	Map<String, String> nLine;
	List<String> qLine;
}

class Bot {

	List<String> find_solution(Model model) {
		List<String> rows = new ArrayList<>();
		for (String FNAME : model.qLine) {
			String EXT = Data.get_file_extension(FNAME);
			boolean isEXTKnown = model.nLine.containsKey(EXT);
			String MT = (isEXTKnown ? model.nLine.get(EXT) : "UNKNOWN");
			rows.add(MT);
		}
		return rows;
	}

}

class Data {

	static String get_file_extension(String FNAME) {
		try {
			int lastIndexOf = FNAME.lastIndexOf(".");
			if (-1 == lastIndexOf)
				return "";
			return FNAME.substring(lastIndexOf + 1);
		} catch (Exception e) {
			return "";
		}
	}
}

class Game {

	Model init(Scanner in, Model model) {
		model.n = in.nextInt();
		model.q = in.nextInt();
		model.nLine = new HashMap<>();
		for (int i = 0; i < model.n; i++) {
			String EXT = in.next();
			String MT = in.next();
			model.nLine.put(EXT, MT);
		}
		in.nextLine();
		model.qLine = new ArrayList<>();
		for (int i = 0; i < model.q; i++) {
			String FNAME = in.nextLine();
			model.qLine.add(FNAME);
		}
		Preconditions.check(model.n == model.nLine.size());
		Preconditions.check(model.q == model.qLine.size());
		Log.debug("%d", model.n);
		Log.debug("%d", model.q);
		for (Entry<String, String> i : model.nLine.entrySet()) {
			Log.debug("%s %s", i.getKey(), i.getValue());
		}
		for (String i : model.qLine) {
			Log.debug("%s", i);
		}
		return model;
	}

	void solve(List<String> outputs) {
		for (String i : outputs) {
			System.out.println(i);
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
