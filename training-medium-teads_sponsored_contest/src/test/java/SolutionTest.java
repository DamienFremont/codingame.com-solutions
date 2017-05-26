import org.junit.Test;

public class SolutionTest {

	@Test
	public void test_case_00() {
		TestCase.execute("00", () -> Solution.main(new String[] { "-debug" }));
	}

	@Test
	public void test_case_01() {
		TestCase.execute("01", () -> Solution.main(new String[] { "-debug" }));
	}

}
