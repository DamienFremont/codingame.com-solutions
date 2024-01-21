package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SolutionExecutor {

  public static String watchSolution(Runnable classUnderTest, String inputArgument) {
    var oldIn = System.in;
    var oldOut = System.out;
    try {
      var inMock = new ByteArrayInputStream(inputArgument.getBytes());
      var outCaptor = new ByteArrayOutputStream();
      var outMock = new PrintStream(outCaptor);
      System.setIn(inMock);
      System.setOut(outMock);
      classUnderTest.run();
      var result = new String(outCaptor.toByteArray(), UTF_8);
//      return result.substring(0, result.length() - 1);
      return result;
    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
  }
}
