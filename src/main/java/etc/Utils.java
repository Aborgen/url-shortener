package etc;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import ninja.utils.NinjaProperties;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
  final static int BASE = 64;
  final static String base64Chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-";
  final static File combinationsDir = new File("./combinations");

  // TODO: Make this method more robust
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
    throw new UnsupportedOperationException("Unimplemented");
  }

  @Inject
  public static String getRealizedUrl(@Named("server_full") String base) {
    return base;
  }

  public static String getAvailableUrl(int digitCount) {
    File d = new File(String.format("%s/%s_digits", combinationsDir, digitCount));
    System.out.println(d);
    System.out.println("HERE IS THE DIR!");
    File[] files = d.listFiles();
    ThreadLocalRandom rand = ThreadLocalRandom.current();
    File file = files[rand.nextInt(files.length)];
    System.out.println(file.toPath());
    String result = "";
    try (
      FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
    ) {

      //          long position = ThreadLocalRandom.current().nextLong(0, urlCount + 1) * (THE_DIGIT + 1);

      // Urls are stored with a 1 byte separator between them.
      int tokenLength = digitCount + 1;
      ByteBuffer buf = ByteBuffer.allocate(digitCount);
      long position = rand.nextLong(fileChannel.size() / tokenLength) * tokenLength;
      fileChannel.read(buf, position);
      result = new String(buf.array(), StandardCharsets.UTF_8);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }
}
