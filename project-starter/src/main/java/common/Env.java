package common;

public class Env {

  public static void read(String args[]) {
    Log.Level_DEBUG = false;
    if (args == null)
      return;
    for (String arg : args) {
      if ("-debug".equals(arg))
        Log.Level_DEBUG = true;
    }
  }
}