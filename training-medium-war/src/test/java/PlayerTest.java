import org.junit.Test;

public class PlayerTest {

	@Test
	public void test_case_01() {
		TestCase.execute("01", () -> Solution.main(new String[]{"-debug"}));
	}

	@Test
	public void test_case_02() {
		TestCase.execute("02", () -> Solution.main(new String[]{"-debug"}));
	}

	@Test
	public void test_case_03() {
		TestCase.execute("03", () -> Solution.main(new String[]{"-debug"}));
	}
	@Test
	public void test_case_04() {
		TestCase.execute("04", () -> Solution.main(new String[]{"-debug"}));
	}

}
