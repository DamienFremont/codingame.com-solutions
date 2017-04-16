import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

public class BotTest {

	Game game = new Game();
	Bot bot = new Bot();

	@Test
	public void updateKnown() {

		String data = "" + //
				"1 2 300 400 5 6 7 8 " + //
				"1 2 301 401 5 6 7 8";
		InputStream stdin = System.in;
		try {
			System.setIn(new ByteArrayInputStream(data.getBytes()));
			Scanner in = new Scanner(System.in);

			game.update(in);
			bot.updateKnown(game);
			game.update(in);
			bot.updateKnown(game);

		} finally {
			System.setIn(stdin);
		}
		assertThat(bot.knownCheckPoints).isNotEmpty().hasSize(2);
	}

	@Test
	public void updateKnown1() {
		Point point = new Point();
		point.x = 100;
		point.y = 200;
		game.nextCheckpoint = point;

		bot.updateKnown(game);

		assertThat(bot.knownCheckPoints).isNotEmpty().hasSize(1).contains(point);
	}

	@Test
	public void updateKnown2() {
		Point p1 = new Point();
		p1.x = 100;
		p1.y = 200;
		game.nextCheckpoint = p1;

		bot.updateKnown(game);

		Point p2 = new Point();
		p2.x = 300;
		p2.y = 400;
		game.nextCheckpoint = p2;

		bot.updateKnown(game);

		assertThat(bot.knownCheckPoints).isNotEmpty().hasSize(2).contains(p1, p2);
	}
}
