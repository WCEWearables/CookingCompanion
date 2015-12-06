package com.wce.wearables.cookingcompanion;
import java.util.ArrayList;

/**
 * A container class that holds all the characteristics or attributes of a recipe to
 * be displayed.
 */
public class Recipe {
    private String image_url;
    private String source_url;
    private String title;
    private String publisher;
    private ArrayList<String> ingredients;
    private ArrayList<String> directions;
    private String prepTime;
    private String cookTime;
    private String readyTime;

    /**
     * Recipe constructor
     * @param image - the image url associated with the recipe
     * @param source - the source url for the recipe
     * @param t - the recipe title
     * @param pub - the publisher of the recipe
     */
    public Recipe(String image, String source, String t, String pub) {
        setImage_url(image);
        setSource_url(source);
        setTitle(t);
        setPublisher(pub);
        setIngredients(new ArrayList<String>());
        setDirections(new ArrayList<String>());
    }

    /**
     * Gets the image url of the recipe
     * @return image url of the recipe
     */
    public String getImage_url() {
        return image_url;
    }

    /**
     * Sets the image url
     * @param image_url The image url to set.
     */
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    /**
     * Gets the source url of the recipe
     * @return source url of the recipe
     */
    public String getSource_url() {
        return source_url;
    }

    /**
     * Sets the source url of the recipe
     * @param source_url The source url to set
     */
    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    /**
     * Gets the title of the recipe
     * @return title of the recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the recipe title
     * @param title the title of the recipe
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the publisher associated with the recipe
     * @return the recipe's publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of the recipe
     * @param publisher the publisher of the recipe
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the ingredients associated with the recipe
     * @return An ArrayList containing the ingredients of the recipe
     */
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the ingredients of the recipe
     * @param ingredients An ArrayList of the ingredients of the recipe
     */
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Gets an ArrayList of the recipe directions
     * @return an ArrayList of the recipe directions as strings
     */
    public ArrayList<String> getDirections() {
        return directions;
    }

    /**
     * Sets the directions associated with the recipe
     * @param directions an ArrayList of strings to set the directions to
     */
    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    /**
     * Gets the preparation time associated with the recipe
     * @return the prep time of the recipe
     */
    public String getPrepTime() {
        return prepTime;
    }

    /**
     * Sets the preparation time associated with the recipe
     * @param prepTime the prep time as a string
     */
    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    /**
     * Gets the cook time associated with the recipe
     * @return the cook time of the recipe
     */
    public String getCookTime() {
        return cookTime;
    }

    /**
     * Sets the cook time associated with the recipe
     * @param cookTime the cook time of the recipe
     */
    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    /**
     * Gets the total time it takes to cook the recipe
     * @return the total time of the recipe
     */
    public String getReadyTime() {
        return readyTime;
    }

    /**
     * Sets the total time it takes to cook the recipe
     * @param readyTime the total time to cook the recipe
     */
    public void setReadyTime(String readyTime) {
        this.readyTime = readyTime;
    }

}

