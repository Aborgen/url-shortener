package etc;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ThreadLocalRandom;

public class FileOperations {
  final static File combinationsDir = new File("./combinations");
  DatabaseActions db;

  public FileOperations(DatabaseActions db) {
    this.db = db;
  }

  public void generateCombinations() {
    db.checkIntegrity();
    try {
      Runtime run = Runtime.getRuntime();
      Process p = run.exec("./generate-combinations");
    }
    catch (IOException e) {
      e.printStackTrace();
      return;
    }
  }

  public String getAvailableUrl(int digitCount) {
    File d = getSubCombinationDir(digitCount);
    File[] files = d.listFiles();
    if (files.length == 0) {
      return null;
    }

    ThreadLocalRandom rand = ThreadLocalRandom.current();
    File file = files[rand.nextInt(files.length)];
    String result = "";
    try (
      FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
    ) {
      // Urls are stored with a 1 byte separator between them.
      int tokenLength = digitCount + 1;
      ByteBuffer buf = ByteBuffer.allocate(digitCount);
      if (fileChannel.size() < tokenLength) {
        return null;
      }
      else if (fileChannel.size() == tokenLength) {
        fileChannel.read(buf, 0);
        file.delete();
      }
      else {
        long position = rand.nextLong(fileChannel.size() / tokenLength) * tokenLength;
        fileChannel.read(buf, position);
        removeTokenFromFile(fileChannel, file.toPath(), position, position + tokenLength);
      }

      result = new String(buf.array(), StandardCharsets.UTF_8);
    }
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    return result;
  }

  public static File getSubCombinationDir(int digitCount) {
    final String suffix = "digits";
    return new File(String.format("%s/%s_%s", combinationsDir, digitCount, suffix));
  }

  private void removeTokenFromFile(FileChannel from, Path filename, long tokenStart, long tokenEnd) throws IOException {
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

    from.close();
    Files.move(newFile, filename, StandardCopyOption.REPLACE_EXISTING);
  }
}
