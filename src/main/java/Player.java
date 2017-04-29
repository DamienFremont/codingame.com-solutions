import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		GameInput.initInput(in);
		// game loop
		while (true) {
			GameInput.turnInput(in);
			String nextAction = Bot.nextAction();
			GameInput.turnOutput(nextAction);
		}
	}
}

enum OutputAction {
	MOVE, WAIT
}

class Bot {
	static String nextAction() {
		return move_to_empty_factory();
	}

	static String move_to_empty_factory() {
		int fromFactory = 1;
		int toFactory = Data.findNearestEmptyFactory();
		int cyborgsSended = 12;
		return OutputAction.MOVE + " " + fromFactory + " " + toFactory + " " + cyborgsSended;
	}

}

class Data {
	static int findNearestEmptyFactory() {
		int res = 0;
		for (Entity entity : GameInput.turn.entities) {
			if (isFactory(entity) && isEmpty(entity)) {
				res = entity.entityId;
			}
		}

		// for (Link i : GameInput.init.links) {
		// boolean isFirst = (res == null);
		// boolean isNear = (res.distance < i.distance);
		// boolean isEmpty = isEmpty(i.);
		return res;
	}

	private static boolean isEmpty(Entity i) {
		return i.arg1 == 0;
	}

	private static boolean isFactory(Entity i) {
		return i.entityType == EntityType.FACTORY;
	}
}

class ModelInputInit {
	int factoryCount;
	int linkCount;
	List<Link> links = new ArrayList<>();

}

class Link {
	int factory1;
	int factory2;
	int distance;
}

class ModelInputTurn {
	int entityCount;
	List<Entity> entities = new ArrayList<>();
}

class Entity {
	int entityId;
	EntityType entityType;
	int arg1;
	int arg2;
	int arg3;
	int arg4;
	int arg5;
}

enum EntityType {
	FACTORY, TROOP
}

class GameInput {
	static ModelInputInit init;
	static ModelInputTurn turn;

	static void initInput(Scanner in) {
		init = new ModelInputInit();
		init.factoryCount = in.nextInt();
		init.linkCount = in.nextInt();
		for (int i = 0; i < init.linkCount; i++) {
			Link link = new Link();
			link.factory1 = in.nextInt();
			link.factory2 = in.nextInt();
			link.distance = in.nextInt();
			init.links.add(link);
		}
		Preconditions.check(init.linkCount == init.links.size());

		Log.debug("factoryCount=%d", init.factoryCount);
		Log.debug("linkCount=%d", init.linkCount);
		Log.debug("id, id, dist");
		for (Link i : init.links) {
			Log.debug("%d %d %d", i.factory1, i.factory2, i.distance);
		}
	}

	static void turnInput(Scanner in) {
		turn = new ModelInputTurn();
		turn.entityCount = in.nextInt();
		for (int i = 0; i < turn.entityCount; i++) {
			Entity e = new Entity();
			e.entityId = in.nextInt();
			e.entityType = EntityType.valueOf(in.next());
			e.arg1 = in.nextInt();
			e.arg2 = in.nextInt();
			e.arg3 = in.nextInt();
			e.arg4 = in.nextInt();
			e.arg5 = in.nextInt();
			turn.entities.add(e);
		}
		Preconditions.check(turn.entityCount == turn.entities.size());

		Log.debug("entityCount=%d", turn.entityCount);
		Log.debug("id, type, arg1, arg2, arg3");
		for (Entity i : turn.entities) {
			Log.debug("%d %s %d %d %d %d %d", i.entityId, i.entityType, i.arg1, i.arg2, i.arg3, i.arg4, i.arg5);
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

class Preconditions {
	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}