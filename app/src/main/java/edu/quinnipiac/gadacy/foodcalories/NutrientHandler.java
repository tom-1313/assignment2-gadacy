package edu.quinnipiac.gadacy.foodcalories;

/*
Thomas Gadacy
Professor Ruby ElKharboutly
Assignment 2 Part 2
 */


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NutrientHandler {

    public String foods[] = { "Cauliflower", "Broccoli", "Cabbage", "Potato", "Eggplant", "Chillies", "Tomato", "Leek", "Corn", "Ginger", "Pumpkin", "Squash", "Cucumber", "Spinach", "Sweet Potato", "Apple", "Apricot", "Avocado", "Banana", "Blueberry", "Blackberry",
                "Orange", "Lime", "Pear", "Passionfruit", "Beef", "Salmon", "Tuna", "Ice cream", "Barley" };

    //This method retrieves the information from the JSON and returns it in a String array.
    public String[] getFoodInfo(String foodInfoJsonStr) throws JSONException {
        JSONObject foodInfoJSONObj = new JSONObject(foodInfoJsonStr);
        JSONObject nutrientsJSONObj = foodInfoJSONObj.getJSONArray("parsed").getJSONObject(0).getJSONObject("food").getJSONObject("nutrients");
        JSONObject imageJSONObj = foodInfoJSONObj.getJSONArray("parsed").getJSONObject(0).getJSONObject("food");
        String foodInfo[] = {foodInfoJSONObj.getString("text"), nutrientsJSONObj.getString("ENERC_KCAL"), nutrientsJSONObj.getString("FAT"), imageJSONObj.getString("image")};
        return foodInfo;
    }
}
