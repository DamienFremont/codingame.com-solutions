import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {

	@Test
	public void test_Data_get_file_extension() {
		String[][] argument_expected_cases = { //
				{ "a", "" }, //
				{ "a.wav", "wav" }, //
				{ "b.wav.tmp", "tmp" }, //
				{ "test.vmp3", "vmp3" }, //
				{ "pdf", "" }, //
				{ ".pdf", "pdf" }, //
				{ "mp3", "" }, //
				{ "report..pdf", "pdf" }, //
				{ "defaultwav", "" }, //
				{ ".mp3.", "" } };
		for (String[] argument_expected : argument_expected_cases) {
			String argument = argument_expected[0];
			String expected = argument_expected[1];

			String result = Data.get_file_extension(argument);

			Assertions //
					.assertThat(result) //
					.isEqualTo(expected);
		}
	}
	
	@Test
	public void test_Data_find_solution2() {


			String result = Data.get_file_extension(".pdf");

			Assertions //
					.assertThat(result) //
					.isEqualTo("pdf");
	}

}
