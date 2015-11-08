package com.wce.wearables.cookingcompanion;

/**
 * Created by Ben Vesel on 11/8/2015.
 */
import java.util.ArrayList;

public class Recipe extends Object{
    private String image_url;
    private String source_url;
    private String title;
    private String publisher;
    private ArrayList<String> ingredients;
    private ArrayList<String> directions;
    private String prepTime;
    private String cookTime;
    private String readyTime;

    //Constructor
    public Recipe(String image, String source, String t, String pub) {
        setImage_url(image);
        setSource_url(source);
        setTitle(t);
        setPublisher(pub);
        setIngredients(new ArrayList<String>());
        setDirections(new ArrayList<String>());
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(String readyTime) {
        this.readyTime = readyTime;
    }

}

