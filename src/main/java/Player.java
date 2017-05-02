import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		GameInput.initInput(in);
		// game loop
		while (true) {
			GameInput.turnInput(in);
			String nextAction = Bot.next_action();
			GameInput.turnOutput(nextAction);
		}
	}
}

enum OutputAction {
	MOVE, WAIT
}

class Bot {

	static String next_action() {
		int from = find_my_next_factory();
		int to = find_nearest_empty_factory(from);
		int send = how_many_to_send(from);
		if (from != -1 && to != -1)
			return OutputAction.MOVE + " " + from + " " + to + " " + send;
		else
			return OutputAction.WAIT + "";
	}

	static int find_my_next_factory() {
		for (Entity i : GameInput.turn.entities) {
			if (Data.is_factory(i) && Data.is_me(i) && Data.has_cyborgs_count_min(i, 4)) {
				return i.entityId;
			}
		}
		return -1;
	}
	static int find_nearest_empty_factory(int from) {
		List<Entity> toFactories = GameInput.turn.entities //
				.stream() //
				.filter(i -> (Data.is_factory(i) && Data.isEmpty(i))) //
				.collect(Collectors.toList());
		Entity nearestFactory = toFactories //
				.stream() //
				.min((p1, p2) -> Integer.compare(//
						Data.distance_between(from, p1.entityId), //
						Data.distance_between(from, p2.entityId)))
				.get();
		return nearestFactory.entityId;
	}
	static int how_many_to_send(int from) {
		int cyborgCount = Data.get_factory(from).arg2;
		return cyborgCount;
	}
}

class Data {



	static Entity get_factory(int entityId) {
		return GameInput.turn.entities //
				.stream() //
				.filter(i -> entityId == i.entityId) //
				.findFirst() //
				.get();
	}

	static int distance_between(int from, int to) {
		Link link = GameInput.init.links //
				.stream() //
				.filter(i -> (i.factory1 == from && i.factory2 == to) //
						|| (i.factory2 == from && i.factory1 == to)) //
				.findFirst() //
				.get();
		return link.distance;
	}

	static boolean has_cyborgs_count_min(Entity e, int min) {
		return e.arg2 >= min;
	}

	static boolean is_me(Entity e) {
		return e.arg1 == 1;
	}

	static boolean isEmpty(Entity e) {
		return e.arg1 == 0;
	}

	static boolean is_factory(Entity i) {
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

		Log.debug("factoryCount");
		Log.debug("linkCount");
		Log.debug("id, id, dist");
		
		Log.debug("%d", init.factoryCount);
		Log.debug("%d", init.linkCount);
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
		
		Log.debug("%d", turn.entityCount);
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