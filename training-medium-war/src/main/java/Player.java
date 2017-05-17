import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Solution {
	public static void main(String args[]) {
		Env.read(args);
		Scanner in = new Scanner(System.in);
		Model model = Game.init(in);
		model = Bot.solve(model);
		Game.play(model);
	}
}

class Model {
	List<String> p1Deck, p2Deck;

	static String[] VALUES = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
	List<String> war;

	int winner;
	int round;
}

class Bot {
	static Model solve(Model m) {
		Log.debug("SOLVE =======================");
		m.war = new ArrayList<>();
		m.round = 0;
		while (!m.p1Deck.isEmpty() && !m.p2Deck.isEmpty()) {
			turn(m);
			int score = fight(m.p1Deck, m.p2Deck, m.war);
			if (score == 1) {
				addCardsToBottom(1, m.p1Deck, m.war);
			} else if (score == -1) {
				addCardsToBottom(2, m.p2Deck, m.war);
			} else if (score == 0) {
				war(m.p1Deck, m.p2Deck, m.war);
			} else {
				throw new RuntimeException();
			}
		}
		m.winner = m.p2Deck.isEmpty() ? 1 : 2;
		Log.debug("Winner : Player %d", m.winner);
		return m;
	}

	private static void war(List<String> p1Deck, List<String> p2Deck, List<String> war) {
		Log.debug("    Step 2 : war");
		war.add(removeTopCardFrom(p1Deck));
		war.add(removeTopCardFrom(p1Deck));
		war.add(removeTopCardFrom(p1Deck));
		war.add(removeTopCardFrom(p2Deck));
		war.add(removeTopCardFrom(p2Deck));
		war.add(removeTopCardFrom(p2Deck));
		int score = fight(p1Deck, p2Deck, war);
		if (score == 1) {
			addCardsToBottom(1, p1Deck, war);
		} else if (score == -1) {
			addCardsToBottom(2, p2Deck, war);
		} else if (score == 0) {
			// TODO
			throw new RuntimeException();
		} else {
			throw new RuntimeException();
		}
	}

	private static void turn(Model m) {
		m.round++;
		Log.debug("Game turn %d", m.round);
		Log.debug("  Player 1 : %s", m.p1Deck);
		Log.debug("  Player 2 : %s", m.p2Deck);
		Log.debug("  War : %s", m.war);
	}

	private static void addCardsToBottom(int i, List<String> deck, List<String> war) {
		Log.debug("  Winner : Player %d", 1);
		deck.addAll(war);
		war.clear();
	}

	private static int fight(List<String> p1Deck, List<String> p2Deck, List<String> warDeck) {
		String p1Card = removeTopCardFrom(p1Deck);
		String p2Card = removeTopCardFrom(p2Deck);
		warDeck.add(p1Card);
		warDeck.add(p2Card);
		int fight = Integer.compare(valueOf(p1Card), valueOf(p2Card));
		Log.debug("    Step 1 : the fight %s,%s", p1Card, p2Card);
		return fight;
	}

	private static String removeTopCardFrom(List<String> deck) {
		return deck.remove(0);
	}

	private static int valueOf(String card) {
		for (int i = 0; i < Model.VALUES.length; i++) {
			if (card.startsWith(Model.VALUES[i]))
				return i;
		}
		throw new RuntimeException();
	}
}

class Game {
	static Model init(Scanner in) {
		Log.debug("INIT =======================");
		Model o = new Model();
		int n = in.nextInt();
		o.p1Deck = initDeck(in, n);
		int m = in.nextInt();
		o.p2Deck = initDeck(in, m);
		return o;
	}

	private static List<String> initDeck(Scanner in, int size) {
		List<String> deck = new ArrayList<>();
		Log.info("%d", size);
		for (int i = 0; i < size; i++) {
			String card = in.next();
			deck.add(card);
			Log.info("%s", card);
		}
		return deck;
	}

	static void play(Model m) {
		Log.debug("PLAY =======================");
		Log.debug("SOLUTION %d %d", m.winner, m.round);
		System.out.println(String.format("%s %s", m.winner, m.round));
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
