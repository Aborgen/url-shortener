package etc;

import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
  static int BASE = 64;
  static String base64Chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-";
  public static boolean validateUrl(String url) {
    try {
      new URL(url);
      return true;
    }
    catch (MalformedURLException e) {
      return false;
    }
  }

  public static String convertDigit(long n) {
    StringBuilder result = new StringBuilder();
    do {
      int remainder = (int) (n % BASE);
      n /= BASE;
      result.append(base64Chars.charAt(remainder));
    }
    while (n > 0);

    result.reverse();
    return result.toString();
  }
}
