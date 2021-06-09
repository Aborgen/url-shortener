package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.Utils;
import ninja.lifecycle.Start;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;


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

    int THE_DIGIT = 4;
    long n = Utils.maxValueGivenNDigits(THE_DIGIT);
    Set<String> set = new HashSet<String>();

    String filename = "TEST";
    try(
      FileOutputStream stream = new FileOutputStream(filename);
    )
    {
      long i = (long) (Math.pow(64, THE_DIGIT - 1));
      for (; i <= n; ++i) {
        String s = Utils.convertDigit(i);
        if (!set.contains(s)) {
          byte[] oldBytes = s.getBytes();
          byte[] newBytes = new byte[oldBytes.length + 1];
          System.arraycopy(oldBytes, 0, newBytes, 0, oldBytes.length);
          newBytes[newBytes.length - 1] = "\\".getBytes()[0];

          stream.write(newBytes);
        }
      }
    }

//    Utils.writeSerializable(availableStrings, "TEST");
  }
}
