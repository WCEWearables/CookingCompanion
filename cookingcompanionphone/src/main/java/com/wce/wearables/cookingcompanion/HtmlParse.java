package com.wce.wearables.cookingcompanion;
import android.util.Log;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Ben Vesel on 10/1/2015.
 *
 * Given a recipe from the api call, find the recipe information through html parsing with JSOUP
 *
 * Will parse recipes from allrecipes and cookingcloset specifically
 */
public class HtmlParse {

    /**
     * Parses the recipe at the corresponding source_url located within the passed in
     * Recipe object
     * @param recipes - a recipe container containing the source_url to gather info from
     */
    public static void parse(Recipe recipes) {

        Document doc = null;
        try {
            doc = Jsoup.connect(recipes.getSource_url()).get();
        } catch(IOException ex) {
            Log.d("HtmlParse", "Error connecting to the website... Retrying");
            try {
                doc = Jsoup.connect(recipes.getSource_url()).get();
            } catch(IOException ex2) {
                Log.d("HtmlParse", "Error connecting to the website.");
            }
        }
        //return if unable to connect to the internet and log in debugger
        if(doc == null) {
            return;
        }

        //Parse from allrecipes
        if(recipes.getSource_url().toUpperCase().contains("ALLRECIPES")) {
            //Parse the directions from allrecipes.com
            Elements directions = doc.select(".step > span");

            for(int i = 0; i < directions.size() - 1; i++) {
                recipes.getDirections().add((directions.get(i).text()));

            }
            //Parse the times from allrecipes.com
            Elements times = doc.select(".prepTime > .prepTime__item");

            recipes.setPrepTime(times.get(1).text().replace("Prep", ""));
            recipes.setCookTime(times.get(2).text().replace("Cook", ""));
            recipes.setReadyTime(times.get(3).text().replace("Ready In", ""));

            //Parse ingredients
            Elements ingredients = doc.select(".checkList__line");

            for(int i = 0; i < ingredients.size() - 3; i++) {
                recipes.getIngredients().add(ingredients.get(i).text().replace("ADVERTISEMENT", ""));
            }

            // closet cooking html scraper
        } else if(recipes.getSource_url().toUpperCase().contains("CLOSETCOOKING")) {
            Elements times = doc.select(".details > span");

            recipes.setPrepTime(times.get(0).text().replace("Prep Time: ", ""));
            recipes.setCookTime(times.get(1).text().replace("Cook Time: ", ""));
            recipes.setReadyTime(times.get(2).text().replace("Total Time: ", ""));

            Elements directions = doc.select(".instructions > [itemprop]");

            //directions
            for(int i = 0; i < directions.size(); i++) {
                recipes.getDirections().add((directions.get(i).text()));
            }

            //ingredients
            Elements ingredients = doc.select(".ingredients > [itemprop]");

            for(int i = 0; i < ingredients.size(); i++) {
                recipes.getIngredients().add(ingredients.get(i).text());
            }
        }
    }
}

