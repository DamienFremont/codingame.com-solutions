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
		boolean pat = false;
		while (!m.p1Deck.isEmpty() && !m.p2Deck.isEmpty() && !pat) {
			turn(m);
			pat = steps(m.p1Deck, m.p2Deck, m.war);
		}
		int winner = pat ? 0 : (m.p2Deck.size() < m.p1Deck.size() ? 1 : 2);
		m.winner = winner;
		Log.debug("Winner : Player %d", m.winner);
		return m;
	}

	private static boolean specialCaseRunsOutOfCards(List<String> p1Deck, List<String> p2Deck) {
		return !(p1Deck.size() > 3 && p2Deck.size() > 3);
	}

	private static boolean war(List<String> p1Deck, List<String> p2Deck, List<String> war) {
		boolean pat = false;
		if (specialCaseRunsOutOfCards(p1Deck, p2Deck)) {
			Log.debug("    Step 2 : war %s", "PAT!");
			pat = true;
		} else {
			war.add(removeTopCardFrom(p1Deck));
			war.add(removeTopCardFrom(p1Deck));
			war.add(removeTopCardFrom(p1Deck));
			war.add(removeTopCardFrom(p2Deck));
			war.add(removeTopCardFrom(p2Deck));
			war.add(removeTopCardFrom(p2Deck));
			Log.debug("    Step 2 : war %s", war);
			pat = steps(p1Deck, p2Deck, war);
		}
		return pat;
	}

	private static boolean steps(List<String> p1Deck, List<String> p2Deck, List<String> war) {
		boolean pat = false;
		int win = battle(p1Deck, p2Deck, war);
		if (win != 0) {
			if (win == 1) {
				addCardsToBottom(1, p1Deck, war);
			} else {
				addCardsToBottom(2, p2Deck, war);
			}
		} else {
			pat = war(p1Deck, p2Deck, war);
		}
		return pat;
	}

	private static void turn(Model m) {
		m.round++;
		Log.debug("Game turn %d", m.round);
		Log.debug("  Player 1 : %s", m.p1Deck);
		Log.debug("  Player 2 : %s", m.p2Deck);
	}

	private static void addCardsToBottom(int i, List<String> deck, List<String> war) {
		Log.debug("  Winner : Player %d", i);
		deck.addAll(war);
		war.clear();
	}

	private static int battle(List<String> p1Deck, List<String> p2Deck, List<String> warDeck) {
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
		System.out.println(m.winner == 0 ? "PAT" : String.format("%s %s", m.winner, m.round));
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
