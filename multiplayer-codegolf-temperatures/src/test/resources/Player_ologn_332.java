import java.util.*;

class Solution {
	public static void main(String s[]) {
		Scanner i = new Scanner(System.in);
		int n = i.nextInt();
		i.nextLine();
		int r = n == 0 ? 0
				: Arrays.stream(i.nextLine().split(" ")).map(a -> Integer.parseInt(a))
						.sorted((a, b) -> Math.abs(a) != Math.abs(b) ? Math.abs(a) - Math.abs(b) : b - a).iterator()
						.next();
		System.out.println(r);
	}
}
