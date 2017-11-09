import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.assertj.core.api.Assertions;

public class TestCase {

	static void execute(String id, Runnable function) {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;
		try {
			// GIVEN
			String argument = loadResource("test_case-" + id + "-input.txt");
			ByteArrayInputStream newIn = new ByteArrayInputStream(argument.getBytes());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream newOut = new PrintStream(baos);
			System.setIn(newIn);
			System.setOut(newOut);
			// WHEN
			function.run();
			String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			// THEN
			String expected = loadResource("test_case-" + id + "-output.txt");
			Assertions.assertThat(result).startsWith(expected);
		} finally {
			System.setIn(oldIn);
			System.setOut(oldOut);
		}
	}

	private static String loadResource(String resourceName) {
		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			File file = new File(classLoader.getResource(resourceName).getFile());
			return new String(Files.readAllBytes(file.toPath()));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
