package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.FileOperations;
import ninja.lifecycle.Start;
import ninja.scheduler.Schedule;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Singleton
public class UrlGenerationService {
  @Inject
  DatabaseActions db;

  @Start(order = 90)
  public void generateUrlFragmentCombinations() {
    FileOperations fileOperations = new FileOperations(db);
    fileOperations.generateCombinations();
  }

  @Schedule(delay = 120, initialDelay = 10, timeUnit = TimeUnit.SECONDS)
  public void queryAvailableCombinations() {
    int digitCount = db.getUrlCardinality();
    File dir = FileOperations.getSubCombinationDir(digitCount);
    if (Arrays.stream(dir.listFiles()).anyMatch(file -> file.length() > 0)) {
      return;
    }

    int nextDigitCount = db.incrementDigitCardinality();
    System.out.println(String.format("Incrementing CURRENT_DIGIT_COUNT to %s", nextDigitCount));
    FileOperations fileOperations = new FileOperations(db);
    fileOperations.generateCombinations();
    System.out.println("Generating new combinations...");
  }
}