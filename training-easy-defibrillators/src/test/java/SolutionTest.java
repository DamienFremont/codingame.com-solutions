import java.io.IOException;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void test_case_01() throws IOException {
		TestCase.execute("01-example", () -> Solution.main(null));
	}

	@Test
	public void test_case_02() throws IOException {
		TestCase.execute("02-exact_position", () -> Solution.main(null));
	}

	@Test
	public void test_case_03() throws IOException {
		TestCase.execute("03-complete_file", () -> Solution.main(null));
	}

	@Test
	public void test_case_04() throws IOException {
		TestCase.execute("04-complete_file_2", () -> Solution.main(null));
	}

}
