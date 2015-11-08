package com.wce.wearables.cookingcompanion;

/**
 * Created by Ben Vesel on 11/8/2015.
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Parser {

    public Parser() {

    }

    public static ArrayList<Recipe> AllRecipes = new ArrayList<Recipe>();

    public static void addRecipe(Recipe e) {
        AllRecipes.add(e);
    }

    public static void main(String [] args) {
        try {
            String ret = retrieveRecipes("Chicken");
            System.out.println(ret);
            ArrayList recipes = tryParseList(ret);
            System.out.println(recipes.get(0));
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static String retrieveRecipes(String searchParams) throws Exception {

        //Build the API string
        String call = "http://food2fork.com/api/search?key=06731f0c3bc47ddff3a7e4c9f139add4&q=" + searchParams;
        System.out.println(call);

        //Call the Food2Fork API
        return readUrl(call);

    }

    static int parseRecipeJSON(ArrayList array) {

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
            System.out.println(i);
            System.out.println(newRecipe.getImage_url());
            System.out.println(newRecipe.getPublisher());
            System.out.println(newRecipe.getSource_url());
            System.out.println(newRecipe.getTitle());

        }

        return 0;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static ArrayList tryParseList(String jsonString) {
        // TODO Auto-generated method stub
        try {

            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject)jsonParser.parse(jsonString);
            JsonArray jsonArr = jo.getAsJsonArray("recipes");
            //jsonArr.
            Gson googleJson = new Gson();
            ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
            System.out.println("List size is : "+jsonObjList.size());
            System.out.println("List Elements are  : "+jsonObjList.toString());

            return jsonObjList;


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void printContents(Recipe r) {
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

