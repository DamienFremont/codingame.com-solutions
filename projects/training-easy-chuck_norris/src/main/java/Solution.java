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
	static int BYTE_SIZE = 8;

	static String solve(String MESSAGE) {
		boolean[] bools = toBooleanArray(MESSAGE, CHARSET_SIZE);
		String answer = toStringSeries(bools);
		return answer.substring(1);
	}

	private static String toStringSeries(boolean[] booleanArray) {
		String answer = "";
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
		return answer;
	}

	static boolean[] shrinkBytesFromTo(boolean[] array, int oldByteSize, int newByteSize) {
		int byteCount = (array.length / oldByteSize);
		int newArraySize = (byteCount * newByteSize);
		boolean[] newArray = new boolean[newArraySize];
		int iNew = 0;
		for (int iByte = 0; iByte < byteCount; iByte++) {
			for (int iBool = 0; iBool < oldByteSize; iBool++) {
				if (iBool < newByteSize) {
					int iOld = iBool + iByte * oldByteSize;
					newArray[iNew] = array[iOld];
					iNew++;
				}
			}
		}
		return newArray;
	}

	static boolean isNewSerie(int serieValue, int myInt) {
		return serieValue != myInt;
	}

	static boolean[] toBooleanArray(String MESSAGE, int charSize) {
		byte[] bytes = MESSAGE.getBytes(CHARSET);
		boolean[] bools = new boolean[bytes.length * charSize];
		int iBools = 0;
		for (int iByte = 0; iByte < bytes.length; iByte++) {
			byte[] bytee = new byte[] { bytes[iByte] };
			boolean[] byteBools = toBooleanArray(bytee);
			byteBools = truncateArray(byteBools, charSize);
			byteBools = reverseArray(byteBools);
			for (int iByteBools = 0; iByteBools < byteBools.length; iByteBools++) {
				bools[iBools] = byteBools[iByteBools];
				iBools++;
			}
		}
		return bools;
	}

	private static boolean[] truncateArray(boolean[] array, int newSize) {
		boolean[] newArray = new boolean[newSize];
		int newArrayIndex = 0;
		for (int oldArrayIndex = 0; oldArrayIndex < array.length; oldArrayIndex++) {
			if (oldArrayIndex < CHARSET_SIZE) {
				newArray[newArrayIndex] = array[oldArrayIndex];
				newArrayIndex++;
			}
		}
		return newArray;
	}

	static boolean[] toBooleanArray(byte[] bytes) {
		BitSet bits = BitSet.valueOf(bytes);
		boolean[] bools = new boolean[bytes.length * 8];
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
