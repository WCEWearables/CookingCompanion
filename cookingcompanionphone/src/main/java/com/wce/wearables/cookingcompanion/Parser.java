package com.wce.wearables.cookingcompanion;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Parser extends AsyncTask<String, String, ArrayList<Recipe>>{

    ArrayList<Recipe> allRecipes = Global.AllRecipes;
    private String stringQuery = null;
    private AppCompatActivity mainActivity = null;


    public Parser(AppCompatActivity main) {
        mainActivity = main;
    }

    /**
     * Adds a recipe to the Recipe container.
     * @param e - the string of the recipe to be added
     */
    private void addRecipe(Recipe e) {
        allRecipes.add(e);
    }

    /**
     * Builds the url for calling the API initially to get the recipes, then calls the url via the
     * readUrl method.
     *
     * @param searchParams The comma separated values being searched on.  Eg: "chicken, cheese, etc"
     * @return The corresponding JSON return string from the initial API call
     */
    private String retrieveRecipes(String[] searchParams) {

        //Build the basic API string
        String call = "http://food2fork.com/api/search?key=06731f0c3bc47ddff3a7e4c9f139add4";

        //build the search parameters onto the api string
        for(String s: searchParams) {
            call += "&q=" + s.trim();
        }

        //Call the Food2Fork API
        Log.d("ParserUrl", "URL is " + call);
        return readUrl(call);

    }


    /**
     * Parses the
     * @param array - the
     *
     */
    private void parseRecipeJSON(ArrayList array) {

        Gson gson = new Gson();

        // Iterate through all recipes and create objects to put in the recipe object array list
        for(int i = 0; i < array.size(); i++) {

            String toParse = array.get(i).toString();
            char[] charArray = toParse.toCharArray();

            String temp1 = toParse.trim();
            String temp2 = temp1.replace("{", "");
            String temp3 = temp2.replace("}", "");

            String[] newArray = temp3.split("[=\\,]");

            //Create new object
            Recipe newRecipe = new Recipe(newArray[11], newArray[7], newArray[5], newArray[1]);

            addRecipe(newRecipe);
        }
    }

    private String readUrl(String urlString) {
        InputStream in = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());

            StringBuilder buffer = new StringBuilder();
            int read;

            //read all the JSON returned into a BufferedReader and return it as a string
            while ((read = in.read()) != -1) {
                buffer.append((char)read);
            }
            return buffer.toString();

        } catch(IOException ex) {
            Log.d("Parser", "Exception Reading from URL: " + ex.getMessage());

        } finally {
            //close the reader BufferedReader
            if (in != null) {
                try {
                    in.close();
                    urlConnection.disconnect();
                }
                catch(Exception ex) {
                    Log.d("Parser", "Exception closing BufferedReader");
                }
            }
        }
        return null;
    }

    private ArrayList tryParseList(String jsonString) {

        try {

            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject)jsonParser.parse(jsonString);
            JsonArray jsonArr = jo.getAsJsonArray("recipes");
            //jsonArr.
            Gson googleJson = new Gson();

            return googleJson.fromJson(jsonArr, ArrayList.class);

        } catch (Exception e) {
            Log.d("Parser", "Error trying to parse list");
        }
        return null;
    }

    /**
     * Debugging function that prints out the contents of all the Recipe contents
     * @param r - the recipe object
     */
    private void printContents(Recipe r) {
        System.out.println("Image url is: " + r.getImage_url());
        System.out.println("Source url is: " + r.getSource_url());
        System.out.println("Title is: " + r.getTitle());
        System.out.println("Publisher is: " + r.getPublisher());

        for(int i = 0; i < r.getIngredients().size(); i++) {
            System.out.println("Ingredient " + (i+1) + " is " + r.getIngredients().get(i));
        }

        for(int i = 0; i < r.getDirections().size(); i++) {
            System.out.println("Direction " + (i+1) + " is " + r.getDirections().get(i));
        }

        System.out.println("Prep time is: " + r.getPrepTime());
        System.out.println("Cook time is: " + r.getCookTime());
        System.out.println("Ready time is: " + r.getReadyTime());
    }

    @Override
    protected ArrayList<Recipe> doInBackground(String ... params) {

        stringQuery =  params[0];
        String[] searchParams = stringQuery.split(",");

        //trip the leading and trailing whitespace if there is any
        for(int i = 0; i < searchParams.length; i++) {
            searchParams[i] = searchParams[i].trim();
        }

        //retreive the JSON response/code for all the recipes
        String jsonRecipes = retrieveRecipes(searchParams);

        Log.d("JsonResponse", "JSON Response is: " + jsonRecipes);
        
        //parse the JSON and then put it into the AllRecipes array
        parseRecipeJSON(tryParseList(jsonRecipes));

        return allRecipes;
    }

    protected void onPostExecute(ArrayList<Recipe> r ) {
        super.onPostExecute(r);

        ArrayList<String> titles = new ArrayList<>();

        //add the recipe titles
        for(int i = 0; i < allRecipes.size(); i++) {

            //only add if the publisher is closetcooking or allrecipes
            if(allRecipes.get(i).getPublisher().toUpperCase().contains("CLOSET COOKING") || allRecipes.get(i).getPublisher().toUpperCase().contains("ALL RECIPES")) {
                titles.add(allRecipes.get(i).getTitle());
            } else {
                allRecipes.remove(allRecipes.get(i));
                i--;
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(mainActivity, R.layout.recipe_list, R.id.recipe_list_textview, titles);
        ListView lv = (ListView) mainActivity.findViewById(R.id.listView);

        lv.setAdapter(adapter);
    }
}

