import org.junit.Test;

public class PlayerTest {
	@Test
	public void test_case_square() {
		TestCase.execute("square", () -> Player.main(new String[] { "-debug" }));
	}

}
