import org.junit.Test;

public class SolutionTest {

	public void test_case_00() {
		TestCase.execute("00", () -> Solution.main(new String[] { "-debug" }));
	}

	public void test_case_01() {
		TestCase.execute("01", () -> Solution.main(new String[] { "-debug" }));
	}

	@Test(timeout = 1000)
	public void test_case_07() {
		TestCase.execute("07", () -> Solution.main(new String[] { "-debug" }));
	}

}
