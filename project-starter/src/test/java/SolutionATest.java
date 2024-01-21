import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static common.SolutionExecutor.watchSolution;
import static org.assertj.core.api.Assertions.assertThat;

public class SolutionATest {

  @ParameterizedTest
  @CsvSource({
          "'1', '1'",
          "'2', '3'",
          "'2', '3'",
  })
  void test_case_01(String arg, String exp) {
    assertThat(
            watchSolution(
                    () -> SolutionA.main(new String[]{"-debug"}),
                    arg))
            .isEqualToIgnoringNewLines(exp);
  }
}
