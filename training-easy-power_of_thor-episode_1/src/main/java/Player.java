import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Game game = new Game();
		Bot bot = new Bot();
		game.init(in);
		while (true) {
			game.turn(in);
			String output = bot.next_action(game);
			game.play(output);
		}
	}
}

class Model {
	static class Point {
		int x;
		int y;
	}
}

class Bot {
	String next_action(Game game) {
		return "SE" + "";
	}
}

class Game {
	Model.Point light;
	Model.Point thor;
	int remainingTurns;

	void init(Scanner in) {
		light = new Model.Point();
		light.x = in.nextInt();
		light.y = in.nextInt();
		thor = new Model.Point();
		thor.x = in.nextInt();
		thor.y = in.nextInt();

	}

	void turn(Scanner in) {
		remainingTurns = in.nextInt();
	}

	void play(String output) {
		System.out.println(output);
	}
}
