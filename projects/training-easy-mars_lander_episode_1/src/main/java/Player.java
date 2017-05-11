import java.util.Scanner;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int surfaceN = in.nextInt();
		for (int i = 0; i < surfaceN; i++) {
			int landX = in.nextInt();
			int landY = in.nextInt();
		}

		while (in.hasNext()) {
			int X = in.nextInt();
			int Y = in.nextInt();
			int hSpeed = in.nextInt();
			int vSpeed = in.nextInt();
			int fuel = in.nextInt();
			int rotate = in.nextInt();
			int power = in.nextInt();

			int outRotate = 0;
			int outPower = 0;
			if (1500 < Y && Y < 3000 && vSpeed < -30)
				outPower = 4;
			else if (1000 < Y && Y < 1500 && vSpeed < -20)
				outPower = 4;
			else if (500 < Y && Y < 1000 && vSpeed < -10)
				outPower = 4;
			else if (0 < Y && Y < 500 && vSpeed < 0)
				outPower = 4;
			System.out.println(String.format("%d %d", outRotate, outPower));
		}
	}
}