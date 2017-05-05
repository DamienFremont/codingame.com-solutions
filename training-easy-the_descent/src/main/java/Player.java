import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Game game = new Game();
		Bot bot = new Bot();
		while (true) {
			game.scanInput(in);
			String output = bot.next_action(game);
			game.play(output);
		}
	}
}

class Model {
	static class Mountain {
		int id;
		int height;
	}
}

class Bot {
	Data data = new Data();

	String next_action(Game game) {
		Model.Mountain heighestMountain = data.find_mountain_by_max_height(game.input);
		int output = heighestMountain.id;
		return output + "";
	}
}

class Data {
	Model.Mountain find_mountain_by_max_height(List<Model.Mountain> input) {
		return input //
				.stream() //
				.max((p1, p2) -> Integer.compare(p1.height, p2.height)) //
				.get();
	}
}

class Game {
	List<Model.Mountain> input;

	void scanInput(Scanner in) {
		input = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			int mountainHeight = in.nextInt();
			Model.Mountain newMountain = new Model.Mountain();
			newMountain.id = i;
			newMountain.height = mountainHeight;
			input.add(newMountain);
		}
	}

	void play(String output) {
		System.out.println(output);
	}
}
