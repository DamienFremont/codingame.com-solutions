import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void test() throws IOException {
		String arg = new String(Files.readAllBytes(Paths.get("test.txt")));
		inputs(arg);
		Player.main(null);
	}

	private void inputs(String inputs) {
		System.setIn(new ByteArrayInputStream(inputs.getBytes()));
	}

}
