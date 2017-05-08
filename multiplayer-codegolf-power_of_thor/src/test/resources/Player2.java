class Player {
	public static void main(String s[]) {
		java.util.Scanner i = new java.util.Scanner(System.in);
		int a = i.nextInt();
		int b = i.nextInt();
		int x = i.nextInt();
		int y = i.nextInt();
		String c[] = { "S", "", "N" };
		String d[] = { "E", "", "W" };
		while (true) {
			int e = (a < x) ? 1 : (x < a) ? -1 : 0;
			int f = (b < y) ? 1 : (y < b) ? -1 : 0;
			String u = c[f + 1] + d[e + 1];
			x -= e;
			y -= f;
			System.out.println(u);
		}
	}
}