import java.util.Scanner;

class Player {
    public static void main(String args[]) {
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
    int nbFloors, width, nbRounds, exitFloor, exitPos;
    int nbTotalClones, nbAdditionalElevators /* , nbElevators */;
    Elevator[] elevators;

    int cloneFloor, clonePos;
    String direction;

    String nextAction;
    
    static class Elevator {
	int floor, pos;
    }
}

class Bot {
    static Model solve(Model m) {
	Log.debug("SOLVE =======================");
	m.nextAction = "WAIT";
	if("RIGHT".equals(m.direction)) {
	    m.nextAction = "BLOCK";
	}
	Log.debug("SOLUTION: ");
	return m;
    }
}

class Game {
    static Model init(Scanner in) {
	Log.debug("INIT =======================");
	Model m = new Model();
	m.nbFloors = in.nextInt();
	m.width = in.nextInt();
	m.nbRounds = in.nextInt();
	m.exitFloor = in.nextInt();
	m.exitPos = in.nextInt();
	m.nbTotalClones = in.nextInt();
	m.nbAdditionalElevators = in.nextInt();
	int nbElevators = in.nextInt();
	Log.debug("%d %d %d %d %d %d %d %d", m.nbFloors, m.width, m.nbRounds, m.exitFloor, m.exitPos, m.nbTotalClones,
	        m.nbAdditionalElevators, nbElevators);
	m.elevators = new Model.Elevator[nbElevators];
	for (int i = 0; i < nbElevators; i++) {
	    m.elevators[i].floor = in.nextInt();
	    m.elevators[i].pos = in.nextInt();
	    Log.debug("%d %d", m.elevators[i].floor, m.elevators[i].pos);
	}
	return m;
    }

    static Model turn(Scanner in, Model m) {
	Log.debug("TURN =======================");
	m.cloneFloor = in.nextInt();
	m.clonePos = in.nextInt();
	m.direction = in.next();
	Log.debug("%d %d %s", m.cloneFloor, m.clonePos, m.direction);
	return m;
    }

    static void play(Model m) {
	System.out.println(m.nextAction);
    }
}

// UTILS

class Log {
    static void debug(String pattern, Object... values) {
	System.err.println(String.format(pattern, values));
    }
}