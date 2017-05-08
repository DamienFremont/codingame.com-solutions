import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class MinifierTest {

	@Test
	public void test_1() throws IOException {
		String argument = getPath("Player.java");
		Minifier.minify(argument);
	}

	private String getPath(String resourceName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		File file = new File(classLoader.getResource(resourceName).getFile());
		return file.toPath().toString();
	}
}
