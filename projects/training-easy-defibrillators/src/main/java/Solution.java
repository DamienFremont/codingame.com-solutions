import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		String LON = in.next();
		String LAT = in.next();
		int N = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		
		String answer = null;
		double answerDist = -1;
		for (int i = 0; i < N; i++) {
			String[] DEFIB = in.nextLine().split(";");
			double dist = distance(LON, LAT, DEFIB[4], DEFIB[5]);
			if (dist < answerDist || answerDist ==-1) {
				answer = DEFIB[1];
				answerDist = dist;
			}
		}

		System.out.println(answer);
	}

	private static double distance(String LON, String LAT, String DEFIB_LON, String DEFIB_LAT) {
		double LON_A = doubleValue(LON);
		double LAT_A = doubleValue(LAT);
		double LON_B = doubleValue(DEFIB_LON);
		double LAT_B = doubleValue(DEFIB_LAT);
		double x = ((LON_B - LON_A) * Math.cos((LAT_A + LAT_B) / 2));
		double y = (LAT_B - LAT_A);
		double dist = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) * 6371);
		return dist;
	}

	private static Double doubleValue(String str) {
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			Number number = format.parse(str);
			double d = number.doubleValue();
			return d;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
