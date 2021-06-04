package controllers;

import com.google.inject.Singleton;
import etc.Utils;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.FlashScope;

@Singleton
public class ShortenController {
  public Result index(@Param("url") String url, FlashScope flashScope) {
    String shortendUrl = shorten(url);
    if (shortendUrl == null) {
      String s = String.format("Invalid URL: %s", url);
      flashScope.error(s);
      return Results.redirect("/");
    }

    return Results.html().render("url", shortendUrl);
  }

  private String shorten(String url) {
    if (!Utils.validateUrl(url)) {
      return null;
    }

    return url;
  }
}
