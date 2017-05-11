import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {

	@Test
	public void test_case_01() throws IOException {
		String argument = loadResource("test_case-01-input.txt");
		String expected = loadResource("test_case-01-output.txt");
		String result = Bot.solve(argument);
		Assertions.assertThat(result).isEqualTo(expected);
	}

	@Test
	public void test_case_02() throws IOException {
		String argument = loadResource("test_case-02-input.txt");
		String expected = loadResource("test_case-02-output.txt");
		String result = Bot.solve(argument);
		Assertions.assertThat(result).isEqualTo(expected);
	}

	@Test
	public void test_case_03() throws IOException {
		String argument = loadResource("test_case-03-input.txt");
		String expected = loadResource("test_case-03-output.txt");
		String result = Bot.solve(argument);
		Assertions.assertThat(result).isEqualTo(expected);
	}

	@Test
	public void test_case_04() throws IOException {
		String argument = loadResource("test_case-04-input.txt");
		String expected = loadResource("test_case-04-output.txt");
		String result = Bot.solve(argument);
		Assertions.assertThat(result).isEqualTo(expected);
	}

	private String loadResource(String resourceName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		File file = new File(classLoader.getResource(resourceName).getFile());
		return new String(Files.readAllBytes(file.toPath()));
	}

	@Test
	public void test_shrinkBytes_one() throws IOException {
		boolean[] argument = new boolean[] { //
				true, true, false };
		boolean[] expected = new boolean[] { //
				true, true };
		boolean[] result = Bot.shrinkBytesFromTo(argument, 3, 2);
		Assertions.assertThat(result).isEqualTo(expected);
	}

	@Test
	public void test_shrinkBytes_serie_of_2() throws IOException {
		boolean[] argument = new boolean[] { //
				true, true, false, //
				true, false, false };
		boolean[] expected = new boolean[] { //
				true, true, //
				true, false };
		boolean[] result = Bot.shrinkBytesFromTo(argument, 3, 2);
		Assertions.assertThat(result).isEqualTo(expected);
	}

	@Test
	public void test_shrinkBytes_serie_of_3() throws IOException {
		boolean[] argument = new boolean[] { //
				true, true, false, //
				true, false, false, //
				true, true, false };
		boolean[] expected = new boolean[] { //
				true, true, //
				true, false, //
				true, true };
		boolean[] result = Bot.shrinkBytesFromTo(argument, 3, 2);
		Assertions.assertThat(result).isEqualTo(expected);
	}
}
