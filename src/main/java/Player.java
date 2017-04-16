import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		GameInput.initInput(in);
		// game loop
		while (true) {
			GameInput.turnInput(in);
			GameInput.turnOutput(Bot.nextAction());
		}
	}
}

enum OutputAction {
	MOVE, WAIT
}

class Bot {
	static String nextAction() {
		return OutputAction.MOVE + " "+1+" "+4+" "+12;
	}
}

class GameInput {
	static void initInput(Scanner in) {
		int factoryCount = in.nextInt(); // the number of factories
		int linkCount = in.nextInt(); // the number of links between factories
		Log.debug("factoryCount=%d", factoryCount);
		Log.debug("linkCount=%d", linkCount);
		Log.debug("id, id, dist");
		for (int i = 0; i < linkCount; i++) {
			int factory1 = in.nextInt();
			int factory2 = in.nextInt();
			int distance = in.nextInt();
			Log.debug("%d %d %d", factory1, factory2, distance);
		}
	}

	static void turnInput(Scanner in) {
		int entityCount = in.nextInt();
		Log.debug("entityCount=%d", entityCount);
		Log.debug("id, type, arg1, arg2, arg3");
		for (int i = 0; i < entityCount; i++) {
			int entityId = in.nextInt();
			String entityType = in.next();
			int arg1 = in.nextInt();
			int arg2 = in.nextInt();
			int arg3 = in.nextInt();
			int arg4 = in.nextInt();
			int arg5 = in.nextInt();
			Log.debug("%d %s %d %d %d %d %d", entityId, entityType, arg1, arg2, arg3, arg4, arg5);
		}
	}

	static void turnOutput(String output) {
		System.out.println(output);
	}
}

class Log {
	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}