import java.util.ArrayList;
import java.util.List;
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
		Log.info("%s", model.area);

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

// MODEL *************************************************************

class Model {
	int me = 0, enemy1 = 1, enemy2 = 2;
	Area area;
	Info info;
	List<Unit> units, //
			wrecks, reapers, //
			myReapers;
	GameState state;
}

enum GameState {
	PLAY, END
}

class Info {
	int myScore, enemyScore1, enemyScore2;
	int myRage, enemyRage1, enemyRage2;
	int unitCount;
}

class Area {
	int radius = 6000;
	int maxThrottle = 300;
	int minThrottle = 10;
	int stopThrottle = 0;
	double friction = 0.2;

	@Override
	public String toString() {
		return String.format("Area: radius=%d maxThrottle=%d minThrottle=%d", //
				radius, maxThrottle, minThrottle);
	}
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
	REAPER(0), WRECK(4);

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

		Action a = computeAction();
		out.actions.add(a);

		// TODO NEXT LEAGUE
		a = new Action();
		a.wait = true;
		out.actions.add(a);
		out.actions.add(a);
		return out;
	}

	Action computeAction() {

		Unit source = model.myReapers.stream() //
				.findFirst() //
				.get();
		Point2D sourcePt = map(source);
		Log.info("source reaper %s", sourcePt);

		for (Unit w : model.wrecks)
			Log.info("...candidate wrecks %s d=%d", map(w), (int) Maths.distanceBetween(sourcePt, map(w)));

		Unit target = findNextTarget(sourcePt);

		Point2D targetPt = map(target);
		Log.info("target wreck %s", targetPt);

		int distanceBetween = (int) Maths.distanceBetween(sourcePt, targetPt);
		Log.info("distanceBetween=%d", distanceBetween);

		int distanceToStop = distanceToStop(source, target);
		Log.info("distanceToStop=%d", distanceToStop);

		int throttle = (distanceBetween > distanceToStop) ? //
				model.area.maxThrottle : //
				model.area.stopThrottle;

		Action a = new Action();
		a.wait = false;
		a.throttle = throttle;
		a.x = targetPt.x;
		a.y = targetPt.y;

		return a;
	}

	// TODO water level
	private Unit findNextTarget(Point2D sourcePt) {
		Unit target = model.wrecks.stream() //
				.findFirst() //
				.get();
		for (Unit candidate : model.wrecks) {
			int candidateDistance = (int) Maths.distanceBetween(sourcePt, map(candidate));
			int actualDistance = (int) Maths.distanceBetween(sourcePt, map(target));
			if (candidateDistance < actualDistance)
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

	int distanceToStop(Unit reaper, Unit target) {
		double friction = model.area.friction;
		Vector2D vector = new Vector2D(reaper.vx, reaper.vy);
		double v = vector.length();
		Log.info("source reaper velocity=%f", v);
		int d = 0;
		while (v > model.area.minThrottle) {
			d += v;
			v = nextTurnVelocity(v, friction);
			Log.info("...computing distanceToStop: d=%d v=%f", d, v);
		}
		return d;
	}

	double nextTurnVelocity(double v, double friction) {
		v = v * (1 - friction);
		return v;
	}

}

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
		model.reapers = model.units.stream() //
				.filter(i -> (UnitType.REAPER == i.unitType)) //
				.collect(Collectors.toList());
		model.wrecks = model.units.stream() //
				.filter(i -> (UnitType.WRECK == i.unitType)) //
				.collect(Collectors.toList());

		model.myReapers = model.reapers.stream() //
				.filter(i -> model.me == i.player) //
				.collect(Collectors.toList());
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