package etc;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import ninja.utils.NinjaProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
  final static int BASE = 64;
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

  @Inject
  public static String getRealizedUrl(@Named("server_full") String base) {
    return base;
  }

  public static String getAvailableUrl(int digitCount) {
    File d = new File(String.format("%s/%s_digits", combinationsDir, digitCount));
    File[] files = d.listFiles();
    ThreadLocalRandom rand = ThreadLocalRandom.current();
    File file = files[rand.nextInt(files.length)];
    String result = "";
    try (
      FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
    ) {
      // Urls are stored with a 1 byte separator between them.
      int tokenLength = digitCount + 1;
      ByteBuffer buf = ByteBuffer.allocate(digitCount);
      long position = rand.nextLong(fileChannel.size() / tokenLength) * tokenLength;
      fileChannel.read(buf, position);
      result = new String(buf.array(), StandardCharsets.UTF_8);
      removeTokenFromFile(fileChannel, file.toPath(), position, position + tokenLength);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }

  // TODO: Move all file operations to separate class
  private static void removeTokenFromFile(FileChannel from, Path filename, long tokenStart, long tokenEnd) throws IOException {
    Path newFile = Files.createTempFile(null,null);
    try (
      FileChannel writer = FileChannel.open(newFile, StandardOpenOption.WRITE);
    ) {
      from.transferTo(0, tokenStart, writer);
      from.transferTo(tokenEnd, from.size(), writer);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(newFile.toAbsolutePath());
    from.close();
    Files.move(newFile, filename, StandardCopyOption.REPLACE_EXISTING);
  }
}
