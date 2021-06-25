package etc;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
  final static String base64Chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$-";
  final static File combinationsDir = new File("./combinations");

  public static boolean validateUrl(String url) {
    try {
      new URL(url);
      return true;
    }
    catch (MalformedURLException e) {
      return false;
    }
  }

  public static int base64ToInt(String s) {
    int result = 0;
    for (int i = 0; i < s.length(); ++i) {
      char c = s.charAt(i);
      result = result * 64 + base64Chars.indexOf(c);
    }

    return result;
  }
}
