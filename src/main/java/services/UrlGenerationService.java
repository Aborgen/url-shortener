package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.Utils;
import ninja.lifecycle.Start;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


@Singleton
public class UrlGenerationService {
  @Inject
  DatabaseActions db;

  @Start(order = 90)
  public void foo() throws Exception {
//    Set<String> set = db.getCurrentUrlGroup();
//    LinkedList<String> availableStrings = new LinkedList<>();
//
//    long n = Utils.maxValueGivenNDigits(db.getUrlCardinality());
//    for (long i = 0; i < n; ++i) {
//      String s = Utils.parseDigit(i);
//      if (!set.contains(s)) {
//        availableStrings.push(s);
//      }
//    }
//
//    int THE_DIGIT = 4;
//    long n = Utils.maxValueGivenNDigits(THE_DIGIT);
//    Set<String> set = new HashSet<String>();
//
//    String filename = "TEST";
//    try(
//      FileChannel fileChannel = FileChannel.open(Path.of(filename), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
//    )
//    {
//      long i = (long) (Math.pow(64, THE_DIGIT - 1));
//      long urlCount = 0;
//      for (; i <= n; ++i) {
//        String s = Utils.convertDigit(i);
//        if (!set.contains(s)) {
//          byte[] oldBytes = s.getBytes();
//          byte[] newBytes = new byte[oldBytes.length + 1];
//          System.arraycopy(oldBytes, 0, newBytes, 0, oldBytes.length);
//          newBytes[newBytes.length - 1] = "\\".getBytes()[0];
//
//          long position = ThreadLocalRandom.current().nextLong(0, urlCount + 1) * (THE_DIGIT + 1);
//          fileChannel.write(ByteBuffer.wrap(newBytes), position);
//          fileChannel.write(ByteBuffer.wrap(newBytes));
//          ++urlCount;
//        }
//      }
    }
}
