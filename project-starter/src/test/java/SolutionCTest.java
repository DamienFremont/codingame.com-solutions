import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static common.SolutionExecutor.watchSolution;
import static common.TestHelper.inputFile;
import static common.TestHelper.outputFile;
import static org.assertj.core.api.Assertions.assertThat;

public class SolutionCTest {

  @ParameterizedTest
  @CsvSource({
          "C_01",
  })
  void test_case_01(String testId) {
    assertThat(
            watchSolution(
                    () -> SolutionC.main(new String[]{"-debug"}),
                    inputFile(testId)))
            .isEqualTo(outputFile(testId));
  }
}
