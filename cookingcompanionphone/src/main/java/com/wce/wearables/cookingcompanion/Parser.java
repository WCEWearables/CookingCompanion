package com.wce.wearables.cookingcompanion;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Parser {

    private static ArrayList<Recipe> AllRecipes;
    private static String stringQuery;

    public static void main(String [] args) {
        try {

            //read this in from the android application.  Will be a comma separated string value
            String[] searchParams = stringQuery.split(",");
            String ret = retrieveRecipes(searchParams);
            ArrayList recipes = tryParseList(ret);
            parseRecipeJSON(recipes);

            for(int i = 0; i < AllRecipes.size(); i++) {
                if(AllRecipes.get(i).getSource_url().toUpperCase().contains("ALLRECIPES")) {
                    HtmlParse.parse(AllRecipes.get(i));
                    printContents(AllRecipes.get(i));
                    break;
                }
                else if(AllRecipes.get(i).getSource_url().toUpperCase().contains("CLOSETCOOKING")) {
                    HtmlParse.parse(AllRecipes.get(i));
                    printContents(AllRecipes.get(i));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //handles all the functioning for the thingy
    public Parser(String sq) {
        AllRecipes = new ArrayList<>();
        stringQuery = sq;
    }


    public static ArrayList<Recipe> init() {

        String[] searchParams = stringQuery.split(",");

        //trip the leading and trailing whitespace if there is any
        for(int i = 0; i < searchParams.length; i++) {
            searchParams[i] = searchParams[i].trim();
        }

        //retreive the JSON response/code for all the recipes
        String jsonRecipes = retrieveRecipes(searchParams);

        Log.d("HERE", "HERE");

        //parse the JSON and then put it into the AllRecipes array
        parseRecipeJSON(tryParseList(jsonRecipes));

        return AllRecipes;
    }

    /**
     * Adds a recipe to the Recipe container.
     * @param e - the string of the recipe to be added
     */
    private static void addRecipe(Recipe e) {
        AllRecipes.add(e);
    }

    /**
     * Builds the url for calling the API initially to get the recipes, then calls the url via the
     * readUrl method.
     *
     * @param searchParams The comma separated values being searched on.  Eg: "chicken, cheese, etc"
     * @return The corresponding JSON return string from the initial API call
     */
    private static String retrieveRecipes(String[] searchParams) {

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
    private static void parseRecipeJSON(ArrayList array) {

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

    private static String readUrl(String urlString) {
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];

            //read all the JSON returned into a BufferedReader and return it as a string
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();

        } catch(Exception ex) {
            Log.d("Parser", "Exception Reading from URL");

        } finally {
            //close the reader BufferedReader
            if (reader != null) {
                try {
                    reader.close();
                } catch(Exception ex) {
                    Log.d("Parser", "Exception closing BufferedReader");
                }
            }
        }
        return null;
    }

    private static ArrayList tryParseList(String jsonString) {

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
    private static void printContents(Recipe r) {
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
}

