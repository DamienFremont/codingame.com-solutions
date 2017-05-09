import java.util.*;
import java.io.*;
import static java.lang.Math.abs;

class Solution {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		int result = n == 0 ? 0
				: Arrays.stream(in.nextLine().split(" ")).map(a -> Integer.parseInt(a))
						.sorted((a, b) -> abs(a) != abs(b) ? abs(a) - abs(b) : b - a).iterator().next();
		System.out.println(result);
	}
}
