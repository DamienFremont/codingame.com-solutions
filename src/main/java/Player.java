import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Log.debug("inputInit-----");
		int factoryCount = in.nextInt(); // the number of factories
		int linkCount = in.nextInt(); // the number of links between factories
		Log.debug("factoryCount = %d", factoryCount);
		Log.debug("linkCount = %d", linkCount);
		for (int i = 0; i < linkCount; i++) {
			int factory1 = in.nextInt();
			int factory2 = in.nextInt();
			int distance = in.nextInt();

			Log.debug("Factories %d %d %d", factory1, factory2, distance);

		}

		// game loop
		while (true) {
			Log.debug("inputTurn-----");
			int entityCount = in.nextInt(); // the number of entities (e.g.
											// factories and troops)
			Log.debug("entityCount = %d", entityCount);
			for (int i = 0; i < entityCount; i++) {
				int entityId = in.nextInt();
				String entityType = in.next();
				int arg1 = in.nextInt();
				int arg2 = in.nextInt();
				int arg3 = in.nextInt();
				int arg4 = in.nextInt();
				int arg5 = in.nextInt();
				Log.debug("entity id=%d, type=%s, arg1=%d, arg2=%d, arg3=%d", entityId, entityType, //
						arg1, arg2, arg3);
			}

			GameInput.output(OutputAction.WAIT + "");
		}
	}
}

enum OutputAction {
	MOVE, WAIT
}

class GameInput {
	static void inputInit(Scanner in) {

	}

	static void inputTurn(Scanner in) {

	}

	static void output(String output) {
		System.out.println(output);
	}
}

class Log {
	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}