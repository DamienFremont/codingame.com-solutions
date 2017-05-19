import org.junit.Test;

public class PlayerTest {

	@Test
	public void test_case_01() {
		TestCase.execute("01", () -> Player.main(new String[] { "-debug" }));
	}

	@Test
	public void test_case_02() {
		TestCase.execute("02", () -> Player.main(new String[] { "-debug" }));
	}

}
