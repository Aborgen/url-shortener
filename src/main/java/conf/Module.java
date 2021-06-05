package conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.DatabaseActionsImpl;

@Singleton
public class Module extends AbstractModule {
  protected void configure() {
    // bind your injections here!
    bind(DatabaseActions.class).to(DatabaseActionsImpl.class);
  }
}
