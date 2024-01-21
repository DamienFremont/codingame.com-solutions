package common;

public class Log {

  public static boolean Level_DEBUG;

  public static void info(String pattern, Object... values) {
    log(pattern, values);
  }

  public static void debug(String pattern, Object... values) {
    if (!Level_DEBUG)
      return;
    log(pattern, values);
  }

  public static void log(String pattern, Object... values) {
    System.err.println(String.format(pattern, values));
  }
}
