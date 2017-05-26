import org.junit.Test;

public class PlayerTest {

	@Test
	public void test_case_00() {
		TestCase.execute("00", () -> Player.main(new String[] { "-debug" }));
	}

}
