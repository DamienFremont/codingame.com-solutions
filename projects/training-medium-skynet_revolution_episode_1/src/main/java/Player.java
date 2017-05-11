import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Input model = new Input();
		model.init = Game.init(in);
		while (in.hasNext()) {
			model.turn = Game.turn(in);
			model.output = Bot.solve(model);
			Game.play(model.output);
		}
	}
}

class Input {
	Init init;
	Turn turn;
	Output output;

	static class Init {

		int N, L, E;
		List<Link> links = new ArrayList<>();
		List<Integer> indexes = new ArrayList<>();

		static class Link {
			int N1, N2;
		}
	}

	static class Turn {
		int SI;
	}

	static class Output {
		int C1, C2;
	}
}

class Bot {
	static Input.Output solve(Input model) {
		Input.Output output = new Input.Output();
		output.C1 = 1;
		output.C2 = 2;
		return output;
	}
}

class Game {
	static Input.Init init(Scanner in) {
		Input.Init model = new Input.Init();
		model.N = in.nextInt();
		model.L = in.nextInt();
		model.E = in.nextInt();
		Log.debug("%d %d %d", model.N, model.L, model.E);
		Input.Init.Link link = new Input.Init.Link();
		for (int i = 0; i < model.L; i++) {
			link.N1 = in.nextInt();
			link.N2 = in.nextInt();
			model.links.add(link);
			Log.debug("%d %d", link.N1, link.N2);
		}
		int EI;
		for (int i = 0; i < model.E; i++) {
			EI = in.nextInt();
			model.indexes.add(EI);
			Log.debug("%d %d", link.N1, link.N2);
		}
		return model;
	}

	static Input.Turn turn(Scanner in) {
		Input.Turn model = new Input.Turn();
		model.SI = in.nextInt();
		Log.debug("%d", model.SI);
		return model;
	}

	static void play(Input.Output output) {
		System.out.println(String.format("%s %s", output.C1, output.C2));
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