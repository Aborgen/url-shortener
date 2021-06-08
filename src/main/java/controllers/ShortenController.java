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

  String flashErrorMessage;

  public Result index(@Param("url") String url, FlashScope flashScope) {
    String shortenedUrl = shorten(url);
    if (shortenedUrl == null) {
      return redirectWithFlashError(flashScope);
    }

    return Results.html().render("url", shortenedUrl);
  }

  private Result redirectWithFlashError(FlashScope flashScope) {
    flashScope.error(flashErrorMessage);
    return Results.redirect("/");
  }

  private String shorten(String url) {
    if (!Utils.validateUrl(url)) {
      flashErrorMessage = String.format("Invalid URL: '%s'", url);
      return null;
    }

    boolean result = db.setEntry("0", url);
    if (!result) {
      flashErrorMessage = String.format("There was an issue generating your shortened url. Please try again.", url);
      return null;
    }

    return url;
  }
}
