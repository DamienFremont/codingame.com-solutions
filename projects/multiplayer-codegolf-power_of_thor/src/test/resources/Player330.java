import java.util.Scanner;

class Player {
	public static void main(String s[]) {
		Scanner i = new Scanner(System.in);
		int x = i.nextInt();
		int y = i.nextInt();
		int a = i.nextInt();
		int b = i.nextInt();
		while (true) {
			int t = i.nextInt();
			String p = "";
			if (b < y) {
				p += "S";
				b++;
			}
			;
			if (b > y) {
				p += "N";
				b--;
			}
			;
			if (a < x) {
				p += "E";
				a++;
			}
			;
			if (a > x) {
				p += "W";
				a--;
			}
			;
			System.out.println(p);
		}
	}
}