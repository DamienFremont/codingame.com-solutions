import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

// MAIN

class Player {

	public static void main(String args[]) {
		IA bot = new IA();
		Game game = new Game();
		Scanner in = new Scanner(System.in);
		while (true) {
			long startTime = System.currentTimeMillis();
			game.update(in);
			bot.play(game);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			Engine.debug(String.format("elapsedTime= %d ms", elapsedTime));
		}
	}
}

// BOT

class IA {

	public void play(Game game) {
		for (Entry<Integer, Entity> e : game.myShips.entrySet()) {
			Entity ship = e.getValue();
			ActionType nextAction = compute_nextAction(game, ship);
			String action;
			if (nextAction == ActionType.MOVE) {
				Entity nextBarrel = compute_nextBarrel(game, ship);
				action = String.format("%s %d %d", nextAction, nextBarrel.x, nextBarrel.y);
			} else if (nextAction == ActionType.FIRE) {
				Entity nextTarget = compute_nextTarget(game, ship);
				action = String.format("%s %d %d", nextAction, nextTarget.x, nextTarget.y);
			} else {
				action = String.format("%s", nextAction);
			}
			Engine.debug("ship " + ship.id + " action=" + action);
			System.out.println(action);
		}
	}

	public Entity compute_nextTarget(Game game, Entity ship) {
		return game.hisShips.entrySet().iterator().next().getValue();
	}

	public ActionType compute_nextAction(Game game, Entity ship) {
		ActionType nextAction;
		boolean isPairTurn = ((game.turn % 2) == 0);
		if (isPairTurn) {
			nextAction = game.barrels.isEmpty() ? ActionType.WAIT : ActionType.MOVE;
		} else {
			nextAction = ActionType.FIRE;
		}
		return nextAction;
	}

	public Entity compute_nextBarrel(Game game, Entity ship) {
		Preconditions.check(!game.barrels.isEmpty());
		Entity nextBarrel;
		game.barrels.entrySet() //
				.stream() //
				.forEach(e -> e.getValue().distance = distanceBetween(e.getValue(), ship));
		Entity nearestBarrel = game.barrels.entrySet() //
				.stream() //
				.min((e1, e2) -> Integer.compare(e1.getValue().distance, e2.getValue().distance)) //
				.get().getValue();
		nextBarrel = nearestBarrel;
		// TODO more than one : equal distance
		// TODO sitting duck if orientation >< 90
		// TODO eviter mines
		return nextBarrel;
	}

	private int distanceBetween(Entity a, Entity b) {
		double distance = Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
		return (int) distance;
	}

	public boolean isSameCoord(Entity y, Entity i) {
		return (i.x == y.x) && (i.y == y.y);
	}
}

class Engine {
	public static void debug(String str) {
		System.err.println(str);
	}
}

class Game {
	int turn = 0;
	Map<Integer, Entity> entities = null;
	int myShipCount;
	int entityCount;

	Map<Integer, Entity> myShips;
	Map<Integer, Entity> hisShips;
	Map<Integer, Entity> barrels;

	public void update(Scanner in) {
		updateCount(in);
		updateEntities(in);
		updateModel();
		turn++;
		debug();
	}

	private void updateCount(Scanner in) {
		myShipCount = in.nextInt();
		entityCount = in.nextInt();
	}

	private void updateEntities(Scanner in) {
		Map<Integer, Entity> entities = new HashMap<Integer, Entity>();
		for (int i = 0; i < entityCount; i++) {
			Entity entity = nextEntity(in);
			entities.put(entity.id, entity);
		}
		this.entities = entities;
	}

	public Entity nextEntity(Scanner in) {
		Entity entity = new Entity();
		entity.id = in.nextInt();
		entity.type = EntityType.valueOf(in.next());
		entity.x = in.nextInt();
		entity.y = in.nextInt();
		entity.arg1 = in.nextInt();
		entity.arg2 = in.nextInt();
		entity.arg3 = in.nextInt();
		entity.arg4 = in.nextInt();
		return entity;
	}

	private void updateModel() {
		myShips = Data.findByTypeTeam(entities, EntityType.SHIP, 1);
		hisShips = Data.findByTypeTeam(entities, EntityType.SHIP, 0);
		barrels = Data.findByType(entities, EntityType.BARREL);
		Preconditions.check(myShips.size() == myShipCount);
	}

	public void debug() {
		Engine.debug("gameState.turn=" + turn);
		for (Entry<Integer, Entity> entity : entities.entrySet()) {
			Engine.debug("entity=" + entity.getValue().toString());
		}
	}

}

// UTILS

class Data {

	public static Map<Integer, Entity> findByType(Map<Integer, Entity> entities, EntityType type) {
		return entities.entrySet() //
				.stream() //
				.filter(e -> (type == e.getValue().type)) //
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}

	public static Map<Integer, Entity> findByTypeTeam(Map<Integer, Entity> entities, EntityType type, int team) {
		return entities.entrySet() //
				.stream() //
				.filter(e -> (type == e.getValue().type)) //
				.filter(e -> (team == e.getValue().arg4)) //
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}
}

// MODEL

class Entity {
	int id;
	int x;
	int y;

	EntityType type;
	int arg1;
	int arg2;
	int arg3;
	int arg4;

	@Override
	public String toString() {
		return type + " [id=" + id + ", x=" + x + ", y=" + y + " ]";
	}

	int distance;
}

enum EntityType {
	SHIP, BARREL, MINE, CANNONBALL;
}

enum ActionType {
	MOVE, FIRE, MINE, SLOWER, WAIT
}

// UTILS

class Preconditions {
	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}
