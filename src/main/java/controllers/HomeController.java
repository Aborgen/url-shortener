package controllers;

import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;

import java.util.PrimitiveIterator;
import java.util.Random;

@Singleton
public class HomeController {
  private final String[] shortStuff = { "enshorten", "make small", "be-tiny", "shortify", "minimize", "shrink", "decrease", "shrivel", "condense", "deflate", "attenuate", "lessen", "thin", "narrowize", "make petite", "make little", "humble", "compress", "make succinct", "limit", "boil down", "narrow", "undersize", "find the diminutive", "restrict", "unbig", "unlong", "slenderize", "make lightweight", "pulverize", "make paltry" };
  private PrimitiveIterator.OfInt numberGen = new Random().ints(0, shortStuff.length).iterator();

  public Result index() {
    int n = numberGen.nextInt();
    return Results.html().render("shortVariant", shortStuff[n]);
  }
}
