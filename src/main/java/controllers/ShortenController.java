package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.Utils;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.FlashScope;

@Singleton
public class ShortenController {
  @Inject
  DatabaseActions db;

  public Result index(@Param("url") String url, FlashScope flashScope) {
    String shortenedUrl = shorten(url);
    if (shortenedUrl == null) {
      String s = String.format("Invalid URL: '%s'", url);
      flashScope.error(s);
      return Results.redirect("/");
    }

    return Results.html().render("url", shortenedUrl);
  }

  private String shorten(String url) {
    if (!Utils.validateUrl(url)) {
      return null;
    }

    db.setEntry("0", url);
    return url;
  }
}
