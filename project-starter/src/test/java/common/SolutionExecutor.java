package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SolutionExecutor {

  public static String watchSolution(Runnable classUnderTest, String inputArgument) {
    var result = "";
    var oldIn = System.in;
    var oldOut = System.out;
    try {
      var inMock = new ByteArrayInputStream(inputArgument.getBytes());
      var outCaptor = new ByteArrayOutputStream();
      var outMock = new PrintStream(outCaptor);
      System.setIn(inMock);
      System.setOut(outMock);
      classUnderTest.run();
      result = new String(outCaptor.toByteArray(), UTF_8);
    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    System.out.println("inputArgument:");
    System.out.println(result);
    System.out.println("outputResult");
    System.out.println(result);
    return result;
  }
}
