package com.wce.wearables.cookingcompanion;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * Given a recipe from the api call, find the recipe information through html parsing with JSOUP
 *
 * Will parse recipes from allrecipes and cookingcloset specifically
 */
public class HtmlParse extends AsyncTask<Recipe, Object, Recipe> {
    private Bitmap recipeImage = null;
    private AppCompatActivity mainActivity;

    public HtmlParse(AppCompatActivity main) {
        mainActivity = main;
    }

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

    @Override
    protected Recipe doInBackground(Recipe... params) {

        parse(params[0]);

        try {
            //get the image associated with the recipe
            URL url = new URL(params[0].getImage_url());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            recipeImage = BitmapFactory.decodeStream(input);

        } catch(IOException ex) {
            Log.d("HtmlParse", "Error parsing the image");
        }

        return params[0];
    }


    @Override
    protected void onPostExecute(Recipe o) {
        super.onPostExecute(o);

        ImageView imageView = (ImageView) mainActivity.findViewById(R.id.recipe_image);
        TextView titleView = (TextView) mainActivity.findViewById(R.id.recipe_title);
        TextView bodyView = (TextView) mainActivity.findViewById(R.id.recipe_body);

        imageView.setImageBitmap(recipeImage);

        //sets the title
        titleView.setText(o.getTitle() + "\r\n");

        //outputs the directions to the screen
        for(int i = 0; i < o.getDirections().size(); i++) {
            bodyView.append("Step " + (i+1) + " " + o.getDirections().get(i) + "\r\n\r\n");

        }
    }



}

