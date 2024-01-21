package common;

public class Preconditions {

  public static void check(boolean condition) {
    if (!condition)
      throw new RuntimeException("CONDITION FALSE");
  }
}