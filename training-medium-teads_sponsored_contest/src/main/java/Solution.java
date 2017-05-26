import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Solution {

	public static void main(String args[]) {
		Env.read(args);

		Log.info("INIT =======================");
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		Log.info("%d", n);
		Graph<Person> people = new Graph<>();
		for (int i = 0; i < n; i++) {
			Person p1 = findOrCreate(people, in.nextInt());
			Person p2 = findOrCreate(people, in.nextInt());
			p1.adjacents.add(p2);
			p2.adjacents.add(p1);
			Log.info("%d %d", p1.vertex, p2.vertex);
		}
		Log.debug("People Graph:");
		Log.debug("  nodes=%d", people.vertices.size());
		for (Person p : people.vertices) {
			Log.debug("  Person Node(vertex=%s, adjacents=%s)", p.toString(), p.adjacents.stream() //
					.map(i -> i.vertex).collect(Collectors.toList()));
		}

		Log.info("SOLVE =======================");

		int steps = -1;
		for (Person testNode : people.vertices) {
			people.vertices.stream() //
					.forEach(i -> i.message = false);
			long propagandaCount = 0;
			Log.debug("testNode=%s", testNode);
			testNode.message = true;

			int testSteps = 0;
			while (propagandaCount < people.vertices.size() && testSteps < 10) {
				Log.debug("  testSteps=%d", testSteps);
				Log.debug("    propagandaCount=%d", propagandaCount);
				List<Person> actualPeople = people.vertices.stream()//
						.filter(i -> i.message == true) //
						.collect(Collectors.toList());
				Log.debug("    actualPeople=%s", actualPeople);
				List<Person> nextPeople = new ArrayList<>();
				actualPeople.forEach(i -> nextPeople //
						.addAll( //
								i.adjacents.stream() //
										.filter(j -> j.message == false) //
										.collect(Collectors.toList())));
				Log.debug("    nextPeople=%s", nextPeople);
				nextPeople.forEach(i -> i.message = true);
				testSteps++;
				propagandaCount = people.vertices.stream() //
						.filter(i -> i.message == true) //
						.count();
			}
			if (steps == -1 || testSteps < steps)
				steps = testSteps;
		}
		Log.info("steps=%d", steps);

		Log.info("ANSWER =======================");
		System.out.println(steps);
	}

	private static Person findOrCreate(Graph<Person> people, int p1_id) {
		return people.vertices.stream() //
				.filter(p -> p.vertex == p1_id)//
				.findFirst() //
				.orElseGet(() -> {
					Person p = new Person(p1_id);
					people.vertices.add(p);
					return p;
				});
	}
}

class Person extends Node<Person> {
	boolean message = false;

	public Person(int vertex) {
		super(vertex);
	}

	@Override
	public String toString() {
		return String.format("%d", vertex);
	}
}
// UTILS

class Graph<T extends Node<?>> {
	List<T> vertices = new ArrayList<>();
}

class Node<T extends Node<?>> {
	int vertex;
	List<T> adjacents = new ArrayList<>();

	public Node(int vertex) {
		this.vertex = vertex;
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