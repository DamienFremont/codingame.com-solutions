import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Scanner;

class Solution {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		String MESSAGE = in.nextLine();
		String answer = Bot.solve(MESSAGE);
		System.out.println(answer);
	}
}

class Bot {
	static Charset CHARSET = StandardCharsets.US_ASCII;
	static int CHARSET_SIZE = 7;

	static String solve(String MESSAGE) {
		String answer = "";
		boolean[] booleanArray;
		byte[] bytes = MESSAGE.getBytes(CHARSET);
		booleanArray = toBooleanArray(bytes, CHARSET_SIZE);
		booleanArray = reverseArray(booleanArray);
		int serieValue = -1;
		for (boolean c : booleanArray) {
			int value = (c) ? 1 : 0;
			if (isNewSerie(serieValue, value)) {
				serieValue = value;
				String firstBlock = (c) ? "0" : "00";
				answer += String.format(" %s ", firstBlock);
			}
			answer += "0";
		}
		return answer.substring(1);
	}

	private static boolean isNewSerie(int serieValue, int myInt) {
		return serieValue != myInt;
	}

	static boolean[] toBooleanArray(byte[] bytes, int charsetSize) {
		BitSet bits = BitSet.valueOf(bytes);
		boolean[] bools = new boolean[bytes.length * charsetSize];
		for (int i = bits.nextSetBit(0); i != -1; i = bits.nextSetBit(i + 1)) {
			bools[i] = true;
		}
		return bools;
	}

	static boolean[] reverseArray(boolean[] array) {
		int i = 0;
		int j = array.length - 1;
		boolean tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
		return array;
	}
}
