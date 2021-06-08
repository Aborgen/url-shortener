package etc;

import java.util.Set;

public interface DatabaseActions {
  String getEntry(String key);
  boolean setEntry(String key, String value);
  int getUrlCardinality();
  Set<String> getCurrentUrlGroup();
}
