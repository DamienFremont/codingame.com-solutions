import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static common.SolutionExecutor.watchSolution;
import static org.assertj.core.api.Assertions.assertThat;

public class SolutionETest {

  @ParameterizedTest
  @CsvSource({
          "'5 10',     '50'",
  })
  void test_unit(String arg, String exp) {
    assertThat(
            watchSolution(
                    () -> SolutionE.main(new String[]{"-debug"}),
                    arg))
            .isEqualToIgnoringNewLines(exp);
  }
}
