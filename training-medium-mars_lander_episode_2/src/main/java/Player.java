import java.util.Scanner;

class Player {
	public static void main(String args[]) {
		Env.read(args);
		Scanner in = new Scanner(System.in);
		Model model = Game.init(in);
		while (in.hasNext()) {
			model = Game.turn(in, model);
			model = Bot.solve(model);
			Game.play(model);
		}
	}
}

class Model {
	Point[] groundPoints;

	int X, Y, hSpeed, vSpeed, fuel, rotate, power;

	static int[][] PHASES = new int[][] {
			// X%, Y%, hSpeed, vSpeed, fuel, rotate, power
			{ -1, 9999, 99, 99, 0, 90, 4 }, // LAUNCH
			{ -1, 2500, 40, 30, 0, 30, 4 }, // FLIP MANEUVER
			{ 1000, 2000, 20, 20, 0, 22, 4 }, // ENTRY BURN
			{ 500, 1500, 10, 10, 0, 10, 4 }, // AERODYNAMIC GUIDANCE
			{ 0, 500, 0, 0, 0, 0, 4 } // VERSTICAL LANDING
	};

	int outputRotate, outputPower;
}

class Bot {
	static Model solve(Model m) {
		int power = -1;
		int rotate = -1;
		Log.debug("SOLVE =======================");
		Point[] flatGround = findFlatGround(m.groundPoints);
		Point target = computeTarget(flatGround);

		int iPhase = findPhaseByY(m);
		int[] phase = Model.PHASES[iPhase];
		Log.info("phase=%d", iPhase);
		int phase_x = phase[0];
		int phase_hSpeed = phase[2];
		int phase_vSpeed = phase[3];
		int phase_rotate = phase[5];
		int phase_power = phase[6];

		if (iPhase == 0) {
			power = phase_power;
			rotate = -phase_rotate;
		} else if (iPhase == 1) {
			power = phase_power;
			rotate = phase_rotate;
		} else {
			int distance = target.y - m.X;
			boolean hToFast = m.hSpeed > phase_hSpeed;
			boolean vToFast = m.vSpeed > phase_vSpeed;
			if (distance < phase_x) {
				if (m.X < target.x)
					rotate = phase_rotate;
				else
					rotate = -phase_rotate;
			} else {
				rotate = phase_rotate;
			}

			int powerV = (m.vSpeed < -phase_vSpeed) ? phase_power / 2 : 0;
			int powerH = phase_power / 2;
			power = (powerV == 0) ? phase_power : (powerV + powerH);
		}

		Preconditions.check(power != -1);
		Preconditions.check(0 <= power && power <= 4);
		Preconditions.check(rotate != -1);
		Preconditions.check(-90 <= rotate && rotate <= 90);
		m.outputPower = power;
		m.outputRotate = rotate;
		return m;
	}

	private static int findPhaseByY(Model m) {
		int iPhase = -1;
		for (int i = 0; i < Model.PHASES.length; i++) {
			int[] phase = Model.PHASES[i];
			int phase_y = phase[1];
			if (m.Y < phase_y) {
				iPhase = i;
			}
		}
		Preconditions.check(iPhase != -1);
		return iPhase;
	}

	private static Point computeTarget(Point[] flatGround) {
		int x = flatGround[0].x;
		int width = width(flatGround);
		int y = flatGround[0].y;
		Point target = new Point(x + width / 2, y);
		Log.debug("target=[%d,%d]", target.x, target.y);
		return target;
	}

	private static int width(Point[] flatGround) {
		return flatGround[1].x - flatGround[0].x;
	}

	private static Point[] findFlatGround(Point[] groundPoints) {
		Point[] flatGround = new Point[2];
		for (int i = 0; i < groundPoints.length; i++) {
			Point p1 = i > 0 ? groundPoints[i] : null;
			Point p2 = i < groundPoints.length - 1 ? groundPoints[i + 1] : null;
			if (p1 != null && p2 != null && p1.y == p2.y) {
				flatGround[0] = p1;
				flatGround[1] = p2;
			}
		}
		Log.debug("flatGround=[%d,%d][%d,%d]", flatGround[0].x, flatGround[0].y, flatGround[1].x, flatGround[1].y);
		return flatGround;
	}
}

class Game {
	static Model init(Scanner in) {
		Log.debug("INIT =======================");
		Model m = new Model();
		int surfaceN = in.nextInt();
		m.groundPoints = new Point[surfaceN];
		Log.info("%d", m.groundPoints.length);
		for (int i = 0; i < surfaceN; i++) {
			m.groundPoints[i] = new Point(in.nextInt(), in.nextInt());
			Log.info("%d %d", m.groundPoints[i].x, m.groundPoints[i].y);
		}
		return m;
	}

	static Model turn(Scanner in, Model m) {
		Log.debug("TURN =======================");
		m.X = in.nextInt();
		m.Y = in.nextInt();
		m.hSpeed = in.nextInt();
		m.vSpeed = in.nextInt();
		m.fuel = in.nextInt();
		m.rotate = in.nextInt();
		m.power = in.nextInt();
		Log.info("%d %d %d %d %d %d %d", m.X, m.Y, m.hSpeed, m.vSpeed, m.fuel, m.rotate, m.power);
		return m;
	}

	static void play(Model m) {
		Log.debug("PLAY =======================");
		Log.debug("SOLUTION %d %d", m.outputRotate, m.outputPower);
		System.out.println(String.format("%d %d", m.outputRotate, m.outputPower));
	}
}

// UTILS

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

class Point {
	int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Preconditions {

	static void check(boolean condition) {
		if (!condition)
			throw new RuntimeException("CONDITION FALSE");
	}
}
