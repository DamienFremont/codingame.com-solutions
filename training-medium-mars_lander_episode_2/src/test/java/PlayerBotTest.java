import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PlayerBotTest {

	static {
		Log.Level_DEBUG = true;
	}
	
	@Test
	public void test_x() {
		int[][] args = new int[][] {
				// t, x0, v0, x
				// NO SPEED
				{ 0, 0, 0, 0 }, //
				{ 1, 0, 0, 0 }, //
				{ 2, 0, 0, 0 }, //
				// SPEED
				{ 0, 0, 2, 0 }, //
				{ 1, 0, 2, 2 }, //
				{ 6, 0, 2, 12 } };
		for (int[] arg : args) {
			double x = Trajectory.x_t(arg[0], arg[1], arg[2]);
			assertThat(Math.round(x)).isEqualTo(arg[3]);
		}
	}

	@Test
	public void test_y() {
		int[][] args = new int[][] {
			// t, x0, v0, x
			// NO SPEED
			{ 0, 0, 0, 0 }, //
			{ 1, 0, 0, 2 }, //
			{ 2, 0, 0, 7 }, //
			// SPEED
			{ 0, 0, 2, 0 }, //
			{ 1, 0, 2, 4 }, //
			{ 6, 0, 2, 79 } };
			for (int[] arg : args) {
				double y = Trajectory.y_t(arg[0], arg[1], arg[2]);
				assertThat(Math.round(y)).isEqualTo(arg[3]);
			}
	}

}
