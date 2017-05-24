import org.junit.Test;

public class SolutionTest {

	private static final int MAX = 1000;

	@Test(timeout = MAX)
	public void test_case_01() {
		TestCase.execute("01", () -> Solution.main(new String[] { "-debug" }));
	}

	@Test(timeout = MAX)
	public void test_case_02() {
		TestCase.execute("02", () -> Solution.main(new String[] { "-debug" }));
	}

	@Test(timeout = MAX)
	public void test_case_03() {
		TestCase.execute("03", () -> Solution.main(new String[] { "-debug" }));
	}

	@Test(timeout = MAX)
	public void test_case_04() {
		TestCase.execute("04", () -> Solution.main(new String[] { "-debug" }));
	}

	@Test(timeout = MAX)
	public void test_case_05() {
		TestCase.execute("05", () -> Solution.main(new String[] {}));
	}

	@Test(timeout = MAX)
	public void test_case_06() {
		TestCase.execute("06", () -> Solution.main(new String[] { "-debug" }));
	}

}
