import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BotTest {
	
	Game game = new Game();
	Bot bot = new Bot();

	@Test
	public void updateKnown() {
		Point point = new Point();
		point.x = 100;
		point.y = 200;
		game.nextCheckpoint = point;
		
		bot.updateKnown(game);

		assertThat(bot.knownCheckPoints).isNotEmpty().hasSize(1);
		assertThat(bot.knownCheckPoints).contains(point);
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

		assertThat(bot.knownCheckPoints).isNotEmpty().hasSize(2);
		assertThat(bot.knownCheckPoints).contains(p1, p2);
	}
}
