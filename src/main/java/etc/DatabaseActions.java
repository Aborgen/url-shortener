package etc;

public interface DatabaseActions {
  String getEntry(String key);
  void setEntry(String key, String value);
}
