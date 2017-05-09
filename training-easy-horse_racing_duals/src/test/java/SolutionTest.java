import java.io.IOException;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void test_case_01() throws IOException {
		TestCase.execute("01-simple", () -> Solution.main(null));
	}

	@Test
	public void test_case_02() throws IOException {
		TestCase.execute("02-horses_in_any_order", () -> Solution.main(null));
	}

}
