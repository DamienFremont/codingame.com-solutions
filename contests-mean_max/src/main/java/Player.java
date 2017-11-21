import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Player {

	public static void main(String args[]) {
		Env.read(args);

		Log.info("INIT =========================");
		Scanner in = new Scanner(System.in);
		Model model = new Model();
		model.area = new Area();
		model.state = GameState.PLAY;
		Bot bot = new Bot(model);

		// game loop
		while (GameState.PLAY == model.state) {
			Log.info("TURN =========================");

			Log.info("INPUT ========================");
			Data.read(model, in);
			Data.prepare(model);

			Log.info("ACTION =======================");
			Log.info("GameState=%s", model.state);
			Output out = bot.computeOutput();

			Log.info("OUTPUT =======================");
			for (Action a : out.actions) {
				Log.info("%s", a);
				System.out.println(a);
			}
		}
	}
}

// IMPL *************************************************************

class Bot {
	Model model;

	Bot(Model model) {
		this.model = model;
	}

	Output computeOutput() {
		Output out = new Output();
		out.actions = new ArrayList<>();

		if (GameState.END == model.state)
			return out;

		out.actions.add(actionReaper());
		out.actions.add(actionDestroyer());

		// TODO NEXT LEAGUE
		Action a = new Action();
		a.wait = true;
		out.actions.add(a);
		return out;
	}

	Action actionDestroyer() {
		Log.info("actionDestroyer");
		Action a = new Action();
		a.wait = true;

		if (model.myDestroyers.isEmpty())
			return a;

		Unit source = getSource(model.myDestroyers);
		Unit target = getTarget(source, model.enemiesReapers);
		int throttle = getThrottle(source, target);

		a.wait = false;
		a.throttle = throttle;
		a.x = target.x;
		a.y = target.y;

		return a;
	}

	Action actionReaper() {
		Log.info("actionReaper");
		Action a = new Action();
		a.wait = true;

		if (model.wrecks.isEmpty())
			return a;
		if (model.myReapers.isEmpty())
			return a;

		Unit source = getSource(model.myReapers);
		Unit target = getTarget(source, model.wrecks);
		int throttle = getThrottle(source, target);

		a.wait = false;
		a.throttle = throttle;
		a.x = target.x;
		a.y = target.y;

		return a;
	}

	int getThrottle(Unit source, Unit target) {
		int distanceBetween = (int) Maths.distanceBetween(map(source), map(target));
		Log.info("...distanceBetween=%d", distanceBetween);
		int distanceToStop = distanceToStop(source, target);
		Log.info("...distanceToStop=%d", distanceToStop);
		int throttle = (distanceBetween > distanceToStop) ? //
				model.unitRules.get(source.unitType).throttle : //
				model.stopThrottle;
		return throttle;
	}

	Unit getTarget(Unit source, List<Unit> list) {
		for (Unit w : list)
			Log.info("... ...candidate %s d=%d", map(w), (int) Maths.distanceBetween(map(source), map(w)));
		Unit target = findNextTarget(map(source), list);
		Point2D targetPt = map(target);
		Log.info("...target %s", targetPt);
		return target;
	}

	Unit getSource(List<Unit> list) {
		Unit source = list.stream() //
				.findFirst() //
				.get();
		Point2D sourcePt = map(source);
		Log.info("...source %s", sourcePt);
		return source;
	}

	// TODO water level
	// TODO avoid enemy
	private Unit findNextTarget(Point2D sourcePt, List<Unit> candidates) {
		Unit target = candidates.stream() //
				.findFirst() //
				.get();
		for (Unit candidate : candidates) {
			int candidateDistance = (int) Maths.distanceBetween(sourcePt, map(candidate));
			int actualDistance = (int) Maths.distanceBetween(sourcePt, map(target));
			boolean isNearest = (candidateDistance < actualDistance);
			boolean isWaterEnough = candidate.extra > model.minWaterLevel;
			if (isNearest && isWaterEnough)
				target = candidate;
		}
		return target;
	}

	static int nearest(Unit e1, Unit e2) {
		return (int) Maths.distanceBetween(map(e1), map(e2));
	}

	static Point2D map(Unit u) {
		return new Point2D(u.x, u.y);
	}

	int distanceToStop(Unit source, Unit target) {
		double f = model.unitRules.get(source.unitType).friction;
		Vector2D vector = new Vector2D(source.vx, source.vy);
		double v = vector.length();
		Log.info("...distanceToStop args: v=%f, f=%f", v, f);
		int d = 0;
		while (v > model.minThrottle) {
			d += v;
			v = nextTurnVelocity(v, f);
			Log.info("... ...computing distanceToStop: d=%d v=%f", d, v);
		}
		return d;
	}

	double nextTurnVelocity(double v, double friction) {
		v = v * (1 - friction);
		return v;
	}

}

// MODEL *************************************************************

class Model {
	int me = 0, enemy1 = 1, enemy2 = 2;
	Area area;
	Info info;
	List<Unit> units, //
			wrecks, //
			myUnits, myReapers, myDestroyers, //
			enemies, enemiesReapers;
	GameState state;

	int minThrottle = 10;
	int stopThrottle = 0;
	int minWaterLevel = 2;

	Map<UnitType, UnitRule> unitRules = initRules();

	static Map<UnitType, UnitRule> initRules() {
		Map<UnitType, UnitRule> map = new HashMap<>();
		map.put(UnitType.REAPER, new UnitRule(UnitType.REAPER, 300, 0.5f, 0.2f));
		map.put(UnitType.DESTROYER, new UnitRule(UnitType.DESTROYER, 300, 1.5f, 0.3f));
		map.put(UnitType.DOOF, new UnitRule(UnitType.DOOF, 300, 1.0f, 0.25f));
		map.put(UnitType.TANKER, new UnitRule(UnitType.TANKER, 500, 2.5f, 0.4f));
		map.put(UnitType.WRECK, new UnitRule(UnitType.WRECK, -1, -1, -1));
		return map;
	}
}

enum GameState {
	PLAY, END
}

class Info {
	int myScore, enemyScore1, enemyScore2;
	int myRage, enemyRage1, enemyRage2;
	int unitCount;
}

class UnitRule {
	UnitType type;
	int throttle;
	float mass;
	float friction;

	UnitRule(UnitType type, int throttle, float mass, float friction) {
		this.type = type;
		this.throttle = throttle;
		this.mass = mass;
		this.friction = friction;
	}
}

class Area {
	int radius = 6000;
}

class Unit {
	int unitId;
	UnitType unitType;
	int player;
	float mass;
	int radius;
	int x, y;
	int vx, vy;
	int extra;
	int extra2;
}

enum UnitType {
	REAPER(0), DESTROYER(1), DOOF(2), TANKER(3), WRECK(4);

	final int value;

	UnitType(int value) {
		this.value = value;
	}

	static UnitType valueOf(int value) {
		return Stream.of(UnitType.values()) //
				.filter(i -> i.value == value) //
				.findFirst() //
				.get();
	}
}

class Output {
	List<Action> actions;
}

class Action {
	boolean wait;
	int x, y;
	int throttle;

	@Override
	public String toString() {
		if (wait)
			return "WAIT";
		return String.format("%d %d %d", x, y, throttle);
	}
}

// DATA *************************************************************

class Data {

	static void read(Model model, Scanner in) {
		List<Unit> units = new ArrayList<>();
		Info info = new Info();
		model.info = info;
		model.units = units;

		if (!in.hasNext()) {
			model.state = GameState.END;
			return;
		}

		int myScore = in.nextInt();
		int enemyScore1 = in.nextInt();
		int enemyScore2 = in.nextInt();
		int myRage = in.nextInt();
		int enemyRage1 = in.nextInt();
		int enemyRage2 = in.nextInt();
		int unitCount = in.nextInt();
		Log.info("%d", myScore);
		Log.info("%d", enemyScore1);
		Log.info("%d", enemyScore2);
		Log.info("%d", myRage);
		Log.info("%d", enemyRage1);
		Log.info("%d", enemyRage2);
		Log.info("%d", unitCount);

		info.myScore = myScore;
		info.enemyScore1 = enemyScore1;
		info.enemyScore2 = enemyScore2;
		info.myRage = myRage;
		info.enemyRage1 = enemyRage1;
		info.enemyRage2 = enemyRage2;
		info.unitCount = unitCount;

		for (int i = 0; i < unitCount; i++) {
			int unitId = in.nextInt();
			int unitType = in.nextInt();
			int player = in.nextInt();
			float mass = Float.valueOf(in.next());
			int radius = in.nextInt();
			int x = in.nextInt();
			int y = in.nextInt();
			int vx = in.nextInt();
			int vy = in.nextInt();
			int extra = in.nextInt();
			int extra2 = in.nextInt();
			Log.info("%d %d %d %s %d" + " %d %d %d %d" + " %d %d", //
					unitId, unitType, player, Strings.fmt(mass), radius, //
					x, y, vx, vy, //
					extra, extra2);

			Unit u = new Unit();
			u.unitId = unitId;
			u.unitType = UnitType.valueOf(unitType);
			u.player = player;
			u.mass = mass;
			u.radius = radius;
			u.x = x;
			u.y = y;
			u.vx = vx;
			u.vy = vy;
			u.extra = extra;
			u.extra2 = extra2;
			units.add(u);
		}
	}

	static void prepare(Model model) {
		model.wrecks = model.units.stream() //
				.filter(i -> (UnitType.WRECK == i.unitType)) //
				.collect(Collectors.toList());

		model.myUnits = model.units.stream() //
				.filter(i -> model.me == i.player) //
				.collect(Collectors.toList());
		model.myReapers = model.myUnits.stream() //
				.filter(i -> (UnitType.REAPER == i.unitType)) //
				.collect(Collectors.toList());
		model.myDestroyers = model.myUnits.stream() //
				.filter(i -> (UnitType.DESTROYER == i.unitType)) //
				.collect(Collectors.toList());

		model.enemies = model.units.stream() //
				.filter(i -> model.me != i.player) //
				.collect(Collectors.toList());
		model.enemiesReapers = model.enemies.stream() //
				.filter(i -> (UnitType.REAPER == i.unitType)) //
				.collect(Collectors.toList());

		Log.info("units %d", model.units.size());
		Log.info("wrecks %d", model.wrecks.size());
		Log.info("myReapers %d", model.myReapers.size());
		Log.info("myDestroyers %d", model.myDestroyers.size());
	}
}

// LIB *************************************************************

class Point2D {
	int x, y;

	Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		Point2D other = (Point2D) obj;
		if ((x == other.x) && (y == other.y))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return String.format("x=%d y=%d", x, y);
	}
}

class Vector2D {
	double dX, dY;

	public Vector2D() {
		dX = dY = 0.0;
	}

	public Vector2D(double dX, double dY) {
		this.dX = dX;
		this.dY = dY;
	}

	public String toString() {
		return "Vector2D(" + dX + ", " + dY + ")";
	}

	public double length() {
		return Math.sqrt(dX * dX + dY * dY);
	}

	public Vector2D add(Vector2D v1) {
		Vector2D v2 = new Vector2D(this.dX + v1.dX, this.dY + v1.dY);
		return v2;
	}

	public Vector2D sub(Vector2D v1) {
		Vector2D v2 = new Vector2D(this.dX - v1.dX, this.dY - v1.dY);
		return v2;
	}

	public Vector2D scale(double scaleFactor) {
		Vector2D v2 = new Vector2D(this.dX * scaleFactor, this.dY * scaleFactor);
		return v2;
	}

	public Vector2D normalize() {
		Vector2D v2 = new Vector2D();

		double length = Math.sqrt(this.dX * this.dX + this.dY * this.dY);
		if (length != 0) {
			v2.dX = this.dX / length;
			v2.dY = this.dY / length;
		}

		return v2;
	}

	public double dotProduct(Vector2D v1) {
		return this.dX * v1.dX + this.dY * v1.dY;
	}

}

class Maths {
	static double distanceBetween(Point2D a, Point2D b) {
		return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
	}
}

// LANG *************************************************************

class Strings {
	static String fmt(double d) {
		if (d == (long) d)
			return String.format("%d", (long) d);
		else
			return String.format("%s", d);
	}
}

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}

class Log {
	static boolean Level_DEBUG;

	static void info(String pattern, Object... values) {
		log(pattern, values);
	}

	static void debug(String pattern, Object... values) {
		if (!Level_DEBUG)
			return;
		log(pattern, values);
	}

	private static void log(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}

class Env {

	static void read(String args[]) {
		Log.Level_DEBUG = false;
		if (args == null)
			return;
		for (String arg : args) {
			if ("-debug".equals(arg))
				Log.Level_DEBUG = true;
		}
	}
}