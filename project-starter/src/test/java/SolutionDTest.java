import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static common.SolutionExecutor.watchSolution;
import static common.TestHelper.inputFile;
import static common.TestHelper.outputFile;
import static org.assertj.core.api.Assertions.assertThat;

public class SolutionDTest {

  @ParameterizedTest
  @CsvSource({
          "D_01",
  })
  void test_files(String testId) {
    assertThat(
            watchSolution(
                    () -> SolutionD.main(new String[]{"-debug"}),
                    inputFile(testId)))
            .isEqualTo(outputFile(testId));
  }

  @ParameterizedTest
  @CsvSource({
          "'12345',     '1\n5'",
          "'141414',    '1\n4'",
          "'7',         '7\n7'",
          "'987654321', '1\n9'",
  })
  void test_unit(String arg, String exp) {
    assertThat(
            watchSolution(
                    () -> SolutionD.main(new String[]{"-debug"}),
                    arg))
            .isEqualToIgnoringNewLines(exp);
  }


}
