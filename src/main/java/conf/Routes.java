package conf;

import controllers.ShortenController;
import ninja.AssetsController;
import ninja.Results;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.HomeController;

public class Routes implements ApplicationRoutes {
  @Override
  public void init(Router router) {
    router.GET().route("/").with(HomeController::index);
    router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
    router.GET().route("/.+").with(() -> Results.redirect("/"));

    router.POST().route("/api/shorten").with(ShortenController::index);
  }
}
