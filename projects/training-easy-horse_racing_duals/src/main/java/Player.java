import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		List<Integer> all = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			int pi = in.nextInt();
			all.add(pi);
		}

		Collections.sort(all, (p1, p2) -> Integer.compare(p1, p2));
		
		int closest = -1;
		for (int i = 0; i < all.size() - 1; i++) {
			int p1 = all.get(i);
			int p2 = all.get(i + 1);
			int diff = Math.abs(p1 - p2);
			if ((diff < closest) || (closest == -1))
				closest = diff;
		}

		System.out.println("" + closest);
	}
}