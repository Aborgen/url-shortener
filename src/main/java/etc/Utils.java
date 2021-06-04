package etc;

import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
  public static boolean validateUrl(String url) {
    try {
      new URL(url);
      return true;
    }
    catch (MalformedURLException e) {
      return false;
    }
  }
}
