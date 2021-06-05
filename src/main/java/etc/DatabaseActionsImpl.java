package etc;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import redis.clients.jedis.Jedis;

public class DatabaseActionsImpl implements DatabaseActions {
  Jedis jedis;

  //database_host and database_port are found in application.conf
  @Inject
  public DatabaseActionsImpl(@Named("database_host") String host, @Named("database_port") String string_port) {
    int port = Integer.parseInt(string_port);
    this.jedis = new Jedis(host, port);
  }

  @Override
  public String getEntry(String key) {
    String value = jedis.get(key);
    return value;
  }

  @Override
  public void setEntry(String key, String value) {
    jedis.set(key, value);
  }
}
