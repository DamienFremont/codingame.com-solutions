import java.util.*;
import static java.lang.Math.abs;

class Solution {
	public static void main(String s[]) {
		Scanner i = new Scanner(System.in);
		int n = i.nextInt();
		i.nextLine();
		int r = n == 0 ? 0
				: Arrays.stream(i.nextLine().split(" ")).map(a -> Integer.parseInt(a))
						.sorted((a, b) -> abs(a) != abs(b) ? abs(a) - abs(b) : b - a).iterator().next();
		System.out.println(r);
	}
}
