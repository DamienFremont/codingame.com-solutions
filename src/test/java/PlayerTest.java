import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void updateKnown() {
		inputs("" + //
				"1 2 300 400 5 6 7 8 " + //
				"1 2 301 401 5 6 7 8 ");

		Player.main(null);

		// assertThat(bot.knownCheckPoints).isNotEmpty().hasSize(2);
	}

	private void inputs(String inputs) {
		InputStream stdin = System.in;
		System.setIn(new ByteArrayInputStream(inputs.getBytes()));
	}

}
