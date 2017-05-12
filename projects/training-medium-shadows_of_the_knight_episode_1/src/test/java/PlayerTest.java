import org.junit.Test;

public class PlayerTest {

	@Test
	public void test_case_01() {
		TestCase.execute("01", () -> Player.main(null));
	}

	@Test
	public void test_case_04() {
		TestCase.execute("04", () -> Player.main(null));
	}

}
