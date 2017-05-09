import java.io.IOException;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void test_case_01() throws IOException {
		TestCase.execute("01-straight_landing", () -> Player.main(null));
	}

}
