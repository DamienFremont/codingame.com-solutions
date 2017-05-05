import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		Game game = new Game();
		Model model = new Model();
		Bot bot = new Bot();

		model = game.init(in, model);
		while (true) {
			model = game.turn(in, model);
			String output = bot.next_action(model);
			game.play(output);
		}
	}
}

class Model {
	Point light;
	Point thor;
	int remainingTurns;

	static class Point {
		int x;
		int y;
	}

	static enum OutputAction {
		N(0, -1, 337, 22), //
		NE(1, -1, 22, 67), //
		E(1, 0, 67, 112), //
		SE(1, 1, 112, 157), //
		S(0, 1, 157, 202), //
		SW(-1, 1, 202, 247), //
		W(-1, 0, 247, 292), //
		NW(-1, -1, 292, 337);

		int xMove;
		int yMove;
		int angleMin;
		int angleMax;

		OutputAction(int xMove, int yMove, int angleMin, int angleMax) {
			this.xMove = xMove;
			this.yMove = yMove;
			this.angleMin = angleMin;
			this.angleMax = angleMax;
		}
	}

}

class Bot {

	String next_action(Model model) {
		String result;
		Log.debug("Thor position =(%d, %d)", model.thor.x, model.thor.y);

		double angle = angle_between(model.thor, model.light);
		Model.OutputAction action = get_action_from_angle(angle);
		update_position(model.thor, action);

		result = action + "";
		return result;
	}

	void update_position(Model.Point thor, Model.OutputAction action) {
		thor.x += action.xMove;
		thor.y += action.yMove;
	}

	Model.OutputAction get_action_from_angle(double angle) {
		for (Model.OutputAction action : Model.OutputAction.values()) {
			if ((action.angleMin >= angle && angle < 0) || (0 <= angle && angle < action.angleMax))
				return action;
			else if ((action.angleMin <= angle) && (angle < action.angleMax))
				return action;
		}
		throw new RuntimeException();
	}

	int angle_between(Model.Point p1, Model.Point p2) {
		int result;

		int difX = p2.x - p1.x;
		int difY = p2.y - p1.y;
		double angle = Math.toDegrees(Math.atan2(difX, -difY));
		angle = angle + Math.ceil(-angle / 360) * 360;
		result = (int) Math.abs(angle);

		Log.debug("angle_between(thor, light)=%d", result);
		return result;
	}

}

class Game {

	Model init(Scanner in, Model model) {
		Model.Point light = new Model.Point();
		Model.Point thor = new Model.Point();
		light.x = in.nextInt();
		light.y = in.nextInt();
		thor.x = in.nextInt();
		thor.y = in.nextInt();
		model.light = light;
		model.thor = thor;
		return model;
	}

	Model turn(Scanner in, Model model) {
		int remainingTurns = in.nextInt();
		model.remainingTurns = remainingTurns;
		return model;
	}

	void play(String output) {
		System.out.println(output);
	}
}

class Log {
	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}
