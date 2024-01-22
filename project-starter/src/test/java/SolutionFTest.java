import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static common.SolutionExecutor.watchSolution;
import static common.TestHelper.inputFile;
import static common.TestHelper.outputFile;
import static org.assertj.core.api.Assertions.assertThat;

public class SolutionFTest {

  @ParameterizedTest
  @CsvSource({
          "F_01",
  })
  void test_files(String testId) {
    assertThat(
            watchSolution(
                    () -> SolutionF.main(new String[]{"-debug"}),
                    inputFile(testId)))
            .isEqualTo(outputFile(testId));
  }
}
