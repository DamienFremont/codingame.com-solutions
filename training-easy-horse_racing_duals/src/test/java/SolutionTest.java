import org.junit.Test;

public class SolutionTest {

	@Test
	public void test_case_01() {
		TestCase.execute("01-simple", () -> Solution.main(null));
	}

	@Test
	public void test_case_02() {
		TestCase.execute("02-horses_in_any_order", () -> Solution.main(null));
	}

	@Test
	public void test_case_03() {
		TestCase.execute("03-many_horses", () -> Solution.main(null));
	}

}
