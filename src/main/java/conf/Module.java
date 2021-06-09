package conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.DatabaseActionsImpl;
import services.UrlGenerationService;

@Singleton
public class Module extends AbstractModule {
  protected void configure() {
    bind(DatabaseActions.class).to(DatabaseActionsImpl.class);
    bind(UrlGenerationService.class);
  }
}
