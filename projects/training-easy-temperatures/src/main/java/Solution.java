import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
	int temperaturesCount;
	List<Integer> temperatures;
}

class Bot {

	String find_solution(Model model) {
		if (model.temperatures.size() == 0) {
			return "0";
		}
		Integer minTempPositiveOrNegative = Collections //
				.min(model.temperatures, (p1, p2) -> Data.difference_from_0_between(p1, p2));
		Integer minDiff = Data.difference_from_0(minTempPositiveOrNegative);
		Integer minTempPositive = model.temperatures //
				.stream() //
				.filter(i -> minDiff == Data.difference_from_0(i)) //
				.max((p1, p2) -> Integer.compare(p1, p2)) //
				.get();
		Log.debug("minTempPositivOrNegative=" + minTempPositiveOrNegative);
		Log.debug("minDiff=" + minDiff);
		Log.debug("minTempPositive=" + minTempPositive);
		return minTempPositive.toString();
	}
}

class Data {

	static int difference_from_0_between(Integer p1, Integer p2) {
		return Integer.compare(//
				difference_from_0(p1), //
				difference_from_0(p2));
	}

	static int difference_from_0(Integer p1) {
		return Math.abs(0 - p1);
	}

}

class Game {

	Model init(Scanner in, Model model) {
		int n = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		String temps = in.nextLine();
		model.temperaturesCount = n;
		if (model.temperaturesCount > 0) {
			model.temperatures = Arrays //
					.asList(temps //
							.split(" ")) //
					.stream() //
					.map(i -> Integer.valueOf(i)) //
					.collect(Collectors.<Integer> toList());
			;
		} else {
			model.temperatures = new ArrayList<>();
		}
		Preconditions.check(model.temperaturesCount == model.temperatures.size());
		Log.debug("temperaturesCount=%d", model.temperaturesCount);
		Log.debug("temperatures=" + model.temperatures);
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
