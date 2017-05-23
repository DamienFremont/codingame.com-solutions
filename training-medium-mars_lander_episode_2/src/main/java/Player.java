
import java.util.Scanner;

class Player {
	public static void main(String args[]) {
		Env.read(args);
		Log.Level_DEBUG = true;

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

	static enum Phase {
		FLIP, ENTRY, GUIDANCE, LANDING
	}

	Point target;
	int outputRotate, outputPower;
}

class Bot {
	static Model solve(Model m) {
		Log.debug("SOLVE =======================");
		Point[] flatGround = flatGround(m.groundPoints);
		m.target = target(flatGround);

		int t = 20; // FUTUR
		int x0 = m.X, y0 = m.Y;
		Vector v0 = new Vector(m.hSpeed, m.vSpeed);
		Vector vG = new Vector(0, 3.711);
		double x10 = Trajectory.x_t(t, x0, v0.x);
		double y10 = Trajectory.y_t(t, y0, v0.y);
		double x10dist = m.target.x - x10;
		double x10dir = Math.signum(x10dist);
		double x10distAbs = Math.abs(x10dist);
		double y0targetDist = Math.abs(y0 - m.target.y);

		Model.Phase phase;
		double xv, yv;
		if (Math.abs(v0.x) > 70) {
			phase = Model.Phase.FLIP;
			double x0dir = Math.signum(v0.x);
			yv = 0;
			xv = (-x0dir * 2);
		} else if (x10distAbs > 500 && y0targetDist > 100) {
			phase = Model.Phase.ENTRY;
			yv = 0;
			xv = (x10dir * 2);
		} else if (y0targetDist > 100) {
			phase = Model.Phase.GUIDANCE;
			yv = -30;
			xv = (x10dir * 2);
		} else {
			phase = Model.Phase.LANDING;
			xv = 0;
			yv = -30;
		}
		Vector v1 = new Vector(xv, yv);

		m = mapShipCommand(m, v0, vG, yv, v1);

		Log.info("phase=%s", phase);
		Log.debug("dist x,y %d,%d", toInt(x10distAbs), toInt(y0targetDist));
		Log.debug("t+10 x(t), y(t)= %d,%d", toInt(x10), toInt(y10));
		Log.debug("xv,yv: %d, %d", toInt(xv), toInt(yv));
		return m;
	}

	private static Model mapShipCommand(Model m, Vector v0, Vector vG, double yv, Vector v1) {
		int power = -1;
		int rotate = -1;
		double angle = Trajectory.angle(v1.x + vG.x, v1.y + vG.y);
		if (-90 < angle && angle < 90) {
			power = (0 < v0.y && v0.y < yv) ? 0 : 4;
			rotate = toInt(-angle);
		} else {
			rotate = 0;
			if (v0.y < -30)
				power = 4;
			else
				power = 0;
		}
		Preconditions.check(0 <= power && power <= 4);
		Preconditions.check(-90 <= rotate && rotate <= 90);
		m.outputPower = power;
		m.outputRotate = rotate;
		return m;
	}

	static int toInt(double dd) {
		Double d = new Double(dd);
		int i = d.intValue();
		return i;
	}

	private static Point target(Point[] flatGround) {
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

	private static Point[] flatGround(Point[] groundPoints) {
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
		Log.info("X Y hSpeed vSpeed fuel rotate power");
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

class Vector {
	double x, y;

	public Vector(double x, double y) {
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

class Trajectory {

	static double x_t(int t, int x0, double v0) {
		double x = x0 + v0 * t;
		return x;
	}

	static double y_t(int t, int y0, double v0) {
		double g = 3.711;
		double y = y0 + (v0 * t) + (0.5 * g) * Math.pow(t, 2);
		return y;
	}

	static double angle(double x, double y) {
		return Math.toDegrees(Math.atan2(x - 0, y - 0));
	}

	static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

}
