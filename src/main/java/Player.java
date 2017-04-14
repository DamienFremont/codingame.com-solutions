import java.util.*;

import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {

	public static void main(String args[]) {

		// OBJECTS
		GameState gameState = new GameState();

		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			int myShipCount = in.nextInt(); // the number of remaining ships
			int entityCount = in.nextInt(); // the number of entities (e.g.
											// ships, mines or cannonballs)
			List<Entity> entities = init(in, entityCount);
			gameState.turn++;
			debug(gameState, entities, "MOVE 11 10");
			for (int i = 0; i < myShipCount; i++) {
				play("MOVE 11 10");
			}
		}
	}

	private static List<Entity> init(Scanner in, int entityCount) {
		List<Entity> entities = new ArrayList<Entity>();
		for (int i = 0; i < entityCount; i++) {
			UnknownEntity entity = new UnknownEntity();
			entity.id = in.nextInt();
			entity.type = in.next();
			entity.x = in.nextInt();
			entity.y = in.nextInt();
			entity.arg1 = in.nextInt();
			entity.arg2 = in.nextInt();
			entity.arg3 = in.nextInt();
			entity.arg4 = in.nextInt();

			switch (entity.type) {
			case "SHIP":
				Ship ship = new Ship();
				ship.id = entity.id;
				ship.x = entity.x;
				ship.y = entity.y;
				ship.orientation = entity.arg1;
				ship.speed = entity.arg2;
				ship.rum = entity.arg3;
				ship.team = entity.arg4;
				entities.add(ship);
				break;
			case "BARREL":
				Barrel barrel = new Barrel();
				barrel.id = entity.id;
				barrel.x = entity.x;
				barrel.y = entity.y;
				barrel.rum = entity.arg1;
				entities.add(barrel);
				break;
			default:
				// ERROR
				break;
			}
		}
		return entities;
	}

	// FUNCTIONS UTILS

	static void debug(String str) {
		System.err.println(str);
	}

	static class Preconditions {
		static void check(boolean condition) {
			if (!condition)
				throw new RuntimeException("CONDITION FALSE");
		}
	}

	static void debug(GameState gameState, List<Entity> entities, String action) {
		debug("gameState.turn=" + gameState.turn);
		for (Entity entity : entities) {
			debug("entity=" + entity.toString());
		}
		debug("action=" + action);
	}

	// FUNCTIONS BOT

	static void play(String action) {
		System.out.println(action); // Any valid action, such as "WAIT" or
									// "MOVE x y"
	}

	// CLASSES

	static class Entity {
		int id;
		int x;
		int y;

		@Override
		public String toString() {
			return "Entity [id=" + id + ", x=" + x + ", y=" + y + " ]";
		}
	}

	static class UnknownEntity extends Entity {
		String type;
		int arg1;
		int arg2;
		int arg3;
		int arg4;
	}

	static class Ship extends Entity {
		int orientation; // [0..5]
		int speed; // [0..2]
		int rum; // [0..X]
		int team;

		@Override
		public String toString() {
			return "Ship [id=" + id + ", x=" + x + ", y=" + y + ", team=" + team + " ]";
		}
	}

	static class Barrel extends Entity {
		int rum; // [0..X]

		@Override
		public String toString() {
			return "Barrel [id=" + id + ", x=" + x + ", y=" + y + " ]";
		}
	}

	static class GameState {
		int turn = 0;
	}
}