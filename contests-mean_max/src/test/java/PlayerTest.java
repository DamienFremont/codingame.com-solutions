import org.junit.Test;

public class PlayerTest {

	@Test
	public void test_1_next() {
		TestCase.execute("1_next", () -> Player.main(new String[] { "-debug" }));
	}

	@Test
	public void test_2_nearest() {
		TestCase.execute("2_nearest", () -> Player.main(new String[] { "-debug" }));
	}
	@Test
	public void test_3_nowrecks() {
		TestCase.execute("3_nowrecks", () -> Player.main(new String[] { "-debug" }));
	}

}
