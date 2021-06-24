package etc;

import com.google.inject.Inject;

import java.io.IOException;

public class FileOperations {
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
    }
  }
}
