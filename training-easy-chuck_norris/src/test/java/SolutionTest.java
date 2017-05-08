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

	private String loadResource(String resourceName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		File file = new File(classLoader.getResource(resourceName).getFile());
		return new String(Files.readAllBytes(file.toPath()));
	}

}
