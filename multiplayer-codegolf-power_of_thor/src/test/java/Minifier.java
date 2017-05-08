import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Minifier {

	static void minify(String resourceName) throws IOException {
		String content = loadResource(resourceName);
		// file
		content = content.replaceAll("\t", ""); // tab
		content = content.replaceAll("\n", ""); // line
		content = content.replaceAll("\r", ""); // line
		// operators
		content = content.replaceAll(" = ", "=");
		content = content.replaceAll(" \\+= ", "\\+=");
		content = content.replaceAll(" \\-= ", "\\-=");
		content = content.replaceAll(" \\? ", "\\?");
		content = content.replaceAll(" \\: ", "\\:");
		content = content.replaceAll(" \\+ ", "\\+");
		content = content.replaceAll(" < ", "<");
		content = content.replaceAll(" > ", ">");
		// arrays
		content = content.replaceAll(" \\(", "\\(");
		content = content.replaceAll(" \\{", "\\{");
		content = content.replaceAll("\\{ ", "\\{");
		content = content.replaceAll(" \\}", "\\}");
		content = content.replaceAll(", ", ",");
		List<String> lines = Arrays.asList(new String[] { content });
		String path = resourceName + ".min";
		Files.write(Paths.get(path), lines);
		System.out.println(path);
		System.out.println(content);
	}

	static private String loadResource(String resourceName) throws IOException {
		File file = new File(resourceName);
		byte[] encoded = Files.readAllBytes(file.toPath());
		return new String(encoded);
	}
}
