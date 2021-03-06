package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etc.DatabaseActions;
import etc.FileOperations;
import etc.Utils;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.FlashScope;
import ninja.utils.NinjaProperties;

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

  @Inject
  NinjaProperties ninjaProperties;
  private String shorten(String url) {
    if (!Utils.validateUrl(url)) {
      flashErrorMessage = String.format("Invalid URL: '%s'", url);
      return null;
    }

    int digitCount = db.getUrlCardinality();
    FileOperations fileOperations = new FileOperations(db);
    String token = fileOperations.getAvailableUrl(digitCount);
    if (token == null) {
      flashErrorMessage = String.format("We seem to have temporarily run out of shortened urls");
      return null;
    }

    int score = Utils.base64ToInt(token);
    boolean result = db.setEntry(token, url, score);
    if (!result) {
      flashErrorMessage = String.format("There was an issue generating your shortened url. Please try again.", url);
      return null;
    }

    return String.format("%s/%s", ninjaProperties.get("server_full"), token);
  }
}
