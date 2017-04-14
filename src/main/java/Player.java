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
			int myShipCount = in.nextInt();
			int entityCount = in.nextInt();
			List<Entity> entities = init(in, entityCount);
			gameState.nextBarrel = (Barrel) entities.get(2);
			gameState.turn++;

			Barrel nextBarrel = compute_nextBarrel(entities, gameState.nextBarrel);
			debug(gameState, entities);
			for (int i = 0; i < myShipCount; i++) {
				// Any valid action, such as "WAIT" or
				// "MOVE x y"
				String action = String.format("MOVE %d %d", nextBarrel.x, nextBarrel.y);
				debug("ship " + i + " action=" + action);
				play(action);
			}
		}
	}

	// FUNCTIONS BOT

	private static Barrel compute_nextBarrel(List<Entity> entities, Barrel nextBarrel) {
		Preconditions.check(nextBarrel!=null);
		boolean isBarrelStillExist = false;
		Barrel bestBarrel = null;
		for (Entity i : entities) {
			Preconditions.check(i!=null);
			if (i instanceof Barrel) {
				bestBarrel = (Barrel) i;
				if (isSameCoord(nextBarrel, i)) {
					isBarrelStillExist = true;
				}
			}
		}
		if (isBarrelStillExist)
			return nextBarrel;
		else
			return bestBarrel;
	}

	private static boolean isSameCoord(Entity y, Entity i) {
		return (i.x == y.x) && (i.y == y.y);
	}

	static void play(String action) {
		System.out.println(action);
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

	static void debug(GameState gameState, List<Entity> entities) {
		debug("gameState.turn=" + gameState.turn);
		debug("gameState.nextBarrel=" + gameState.nextBarrel);
		for (Entity entity : entities) {
			debug("entity=" + entity.toString());
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
		Barrel nextBarrel;
	}
}