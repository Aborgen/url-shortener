package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.FileOperations;
import etc.Utils;
import ninja.lifecycle.Start;



@Singleton
public class UrlGenerationService {
  @Inject
  DatabaseActions db;

  @Start(order = 90)
  public void generateUrlFragmentCombinations() {
    FileOperations fileOperations = new FileOperations(db);
    fileOperations.generateCombinations();
  }
}