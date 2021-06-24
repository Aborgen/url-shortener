package etc;

public interface DatabaseActions {
  String getEntry(String key);
  boolean setEntry(String key, String value, int score);
  int getUrlCardinality();
  void checkIntegrity();
  int incrementDigitCardinality();
}
