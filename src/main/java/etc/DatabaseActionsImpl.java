package etc;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.List;

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
  public boolean setEntry(String key, String value, int score) {
    if (getEntry(key) != null) {
      return false;
    }

    String digitCount = String.format("length_%s", _getUrlCardinality());
    Transaction t = jedis.multi();
    // TODO: What happens if there is an error in one of these operations, but not the other? Seems like there would be an issue if adding to the sorted set fails, but setnx doesn't
    t.zadd(digitCount, score, key);
    t.setnx(key, value);
    List<Object> result = t.exec();
    // Per https://github.com/redis/jedis/blob/4f96e123e42e3265cfe05c0325a9d9793a38e1e2/src/main/java/redis/clients/jedis/Transaction.java#L46-L56
    return !(result == null || result.stream().anyMatch(JedisDataException.class::isInstance));
  }

  @Override
  public int getUrlCardinality() {
    return Integer.parseInt(_getUrlCardinality());
  }

  @Override
  // If the digit count key has not been set yet, set it to 1.
  public void checkIntegrity() {
    jedis.setnx("CURRENT_DIGIT_COUNT", "1");
  }

  private String _getUrlCardinality() {
    return jedis.get("CURRENT_DIGIT_COUNT");
  }
}
