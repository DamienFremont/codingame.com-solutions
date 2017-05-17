import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	int N, L, E, SI, C1, C2;
	List<Link> links;
	List<Integer> gateways;

	static class Link {
		int N1, N2;
	}
}

class Bot {
	static int SCAN_MAX = 3;

	static Model solve(Model model) {
		Log.debug("SOLVE =======================");
		List<Tree<Integer>> threats = new ArrayList<>();
		for (Integer EI : model.gateways) {
			Tree<Integer> tree = scan(model, EI);
			Tree<Integer> solution = tree.nodes.stream() //
					.filter(i -> i.childs.isEmpty()) //
					.min((p1, p2) -> Integer.compare(p1.depth(), p2.depth())) //
					.get();
			threats.add(solution);
		}
		Tree<Integer> nearest = threats.stream() //
				.min((p1, p2) -> Integer.compare(p1.depth(), p2.depth())) //
				.get();
		Model.Link link = find_link(model, nearest.val, nearest.parent.val);
		model.C1 = link.N1;
		model.C2 = link.N2;
		model.links.remove(link);
		Log.debug("SOLUTION (FIN): %s, remove link [%d,%d]", nearest.path(), link.N1, link.N2);
		return model;
	}

	static Tree<Integer> scan(Model model, int EI) {
		int startNode = model.SI;
		int endNode = EI;
		Function<Integer, Boolean> success = i -> (i == endNode);
		Tree<Integer> tree = new Tree<>(startNode);
		tree.childs = childs(model, tree, success);
		return tree;
	}

	private static List<Tree<Integer>> childs(Model model, Tree<Integer> parent, Function<Integer, Boolean> success) {
		List<Tree<Integer>> childs = new ArrayList<>();
		List<Model.Link> links = find_link(model, parent.val);
		for (Model.Link link : links) {
			int id = get_child(link, parent.val);
			Tree<Integer> child = new Tree<>(id, parent);
			if (success.apply(id) || parent.depth() >= SCAN_MAX) {
				// Log.debug("SOLUTION (TMP): %s", child.path());
			} else {
				child.childs = childs(model, child, success);
			}
			childs.add(child);
		}
		return childs;
	}

	private static int get_child(Model.Link link, int val) {
		return val == link.N1 ? link.N2 : link.N1;
	}

	static List<Model.Link> find_link(Model model, int A) {
		return model.links.stream() //
				.filter(i -> i.N1 == A || i.N2 == A) //
				.collect(Collectors.toList());
	}

	static Model.Link find_link(Model model, int A, int B) {
		return model.links.stream() //
				.filter(i -> (i.N1 == A && i.N2 == B)//
						|| (i.N1 == B && i.N2 == A)) //
				.findFirst()//
				.get();
	}

}

class Tree<T> {
	T val;

	List<Tree<T>> childs;
	Tree<T> parent;

	List<Tree<T>> nodes;

	Tree(T val) {
		this.val = val;
		this.nodes = new ArrayList<>();
		this.childs = new ArrayList<>();
	}

	Tree(T val, Tree<T> parent) {
		this(val);
		this.parent = parent;
		this.nodes = parent.nodes;
		this.nodes.add(this);
	}

	public int size() {
		int size = 1;
		for (Tree<?> i : childs)
			size += i.size();
		return size;
	}

	public int height() {
		int height = 1;
		for (Tree<?> i : childs)
			height += i.height();
		return height;
	}

	public int depth() {
		int parentDepth = parent == null ? 0 : parent.depth();
		return 1 + parentDepth;
	}

	public String path() {
		String parenPath = parent == null ? "START" : parent.path();
		return String.format("%s->%s", parenPath, val);
	}
}

class Game {
	static Model init(Scanner in) {
		Log.debug("INIT =======================");
		Model model = new Model();
		model.N = in.nextInt();
		model.L = in.nextInt();
		model.E = in.nextInt();
		Log.debug("%d %d %d", model.N, model.L, model.E);
		model.links = new ArrayList<>();
		for (int i = 0; i < model.L; i++) {
			Model.Link link = new Model.Link();
			link.N1 = in.nextInt();
			link.N2 = in.nextInt();
			model.links.add(link);
			Log.debug("%d %d", link.N1, link.N2);
		}
		model.gateways = new ArrayList<>();
		int EI;
		for (int i = 0; i < model.E; i++) {
			EI = in.nextInt();
			model.gateways.add(EI);
			Log.debug("%d", EI);
		}
		return model;
	}

	static Model turn(Scanner in, Model model) {
		Log.debug("TURN =======================");
		model.SI = in.nextInt();
		Log.debug("%d", model.SI);
		return model;
	}

	static void play(Model output) {
		System.out.println(String.format("%s %s", output.C1, output.C2));
	}
}

class Log {
	static void debug(String pattern, Object... values) {
		System.err.println(String.format(pattern, values));
	}
}
