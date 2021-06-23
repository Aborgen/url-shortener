package controllers;

import com.google.inject.Inject;
import etc.DatabaseActions;
import etc.Utils;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.FlashScope;

import javax.inject.Singleton;

@Singleton
public class RedirectController {
  @Inject
  DatabaseActions db;

  public Result index(@PathParam("fragment") String fragment, FlashScope flashScope) {
    String url = db.getEntry(fragment);
    if (url == null) {
      flashScope.error("There was in issue when attempting to redirect to the url.");
      return Results.redirect("/");
    }
    else if (!Utils.validateUrl(url)) {
      flashScope.error("There was in issue when attempting to redirect to the url.");
      System.out.println(String.format("Issue: %s is stored at %s, and is not a valid URL", url, fragment));
      return Results.redirect("/");
    }

    return Results.redirectTemporary(url);
  }
}
