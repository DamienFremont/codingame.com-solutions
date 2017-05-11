import java.util.ArrayList;
import java.util.List;
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
	int N, L, E, SI, C1, C2;
	List<Link> links;
	List<Integer> gateways;

	static class Link {
		int N1, N2;
		boolean cutted;
	}
}

class Bot {
	static Model solve(Model model) {
		int EI = nearest_gateway(model);
		Model.Link link = find_link(model, EI);
		link.cutted = true;
		model.C1 = link.N1;
		model.C2 = link.N2;
		return model;
	}

	static Model.Link find_link(Model model, int EI) {
		return model.links.stream() //
				.filter(i -> (EI == i.N1 || EI == i.N2) && i.cutted == false) //
				.findFirst().get();
	}

	static int nearest_gateway(Model model) {
		return model.gateways.get(0);
	}
}

class Game {
	static Model init(Scanner in) {
		Log.debug("init");
		Model model = new Model();
		model.N = in.nextInt();
		model.L = in.nextInt();
		model.E = in.nextInt();
		Log.debug("%d %d %d", model.N, model.L, model.E);
		model.links = new ArrayList<>();
		for (int i = 0; i < model.L; i++) {
			Model.Link link = new Model.Link();
			link.N1 = in.nextInt();
			link.N2 = in.nextInt();
			model.links.add(link);
			Log.debug("%d %d", link.N1, link.N2);
		}
		model.gateways = new ArrayList<>();
		int EI;
		for (int i = 0; i < model.E; i++) {
			EI = in.nextInt();
			model.gateways.add(EI);
			Log.debug("%d", EI);
		}
		return model;
	}

	static Model turn(Scanner in, Model model) {
		Log.debug("turn");
		model.SI = in.nextInt();
		Log.debug("%d", model.SI);
		return model;
	}

	static void play(Model output) {
		System.out.println(String.format("%s %s", output.C1, output.C2));
	}
}

class Log {
	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}
