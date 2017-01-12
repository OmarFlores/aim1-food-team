package tu.berlin.dima.food.generation;

/**
 * Created by Jaguar on 1/10/17.
 */

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import tu.berlin.dima.food.classes.OpeningHours;
import tu.berlin.dima.food.classes.Parameters;
import tu.berlin.dima.food.classes.Restaurant;
import tu.berlin.dima.food.utilities.Functions;

public class Apirequestgoogleplaces {

    private String urlprovider;
    private String applicaton_key;
    private URL connection;
    private Parameters parameters;
    private JsonObject restaurants_data;
    private JsonObject restaurant_data;
    private ArrayList<Restaurant> restaurants;

    final static Logger logger = LogManager.getLogger(Apirequestgoogleplaces.class);
    final static String type = "restaurant";

    public Apirequestgoogleplaces(){
        restaurants = new ArrayList<Restaurant>();
    }

    public Apirequestgoogleplaces(String provider, String urlprovider, String applicaton_key, URL connection, Parameters parameters) {
        this.urlprovider = urlprovider;
        this.applicaton_key = applicaton_key;
        this.connection = connection;
        this.parameters = parameters;
        restaurants_data = null;
        restaurant_data= null;
        restaurants = new ArrayList<Restaurant>();
    }

    public String getUrlprovider() {
        return urlprovider;
    }

    public void setUrlprovider(String urlprovider) {
        this.urlprovider = urlprovider;
    }

    public String getApplicaton_key() {
        return applicaton_key;
    }

    public void setApplicaton_key(String applicaton_key) {
        this.applicaton_key = applicaton_key;
    }

    public URL getConnection() {
        return connection;
    }

    public void setConnection(URL connection) {
        this.connection = connection;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public JsonObject getRestaurants_data() {
        return restaurants_data;
    }

    public void setRestaurants_data(JsonObject restaurants_data) {
        this.restaurants_data = restaurants_data;
    }

    public void getJSONStreamData(){
        InputStream is;
        //System.out.println(this.getFormattedUrlApiRequestSearch());
        try {
            this.connection = new URL(this.getFormattedUrlApiRequestSearch());
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonArray results = restaurants_data.getJsonArray("results");
            for (JsonObject result : results.getValuesAs(JsonObject.class) ){
                System.out.println(result.getString("name"));
                System.out.println(result.getJsonString("vicinity"));
                System.out.println(Functions.getCleanStringfromJSONString(result.getJsonString("place_id")));
                restaurants.add(this.getRestaurantData(
                        Functions.getCleanStringfromJSONString(result.getJsonString("place_id"))));
                System.out.println("-----------");
            }
        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + ex.toString());
        }
    }


    public Restaurant getRestaurantData(String place_id){
        InputStream inputStream;
        Restaurant restaurant = new Restaurant();
        //System.out.println(this.getFormattedUrlApiRequestRestaurant(place_id));
        try {
            URL connection = new URL(this.getFormattedUrlApiRequestRestaurant(place_id));
            inputStream = connection.openStream();
            JsonReader reader = Json.createReader(inputStream);
            this.restaurant_data = reader.readObject();

            JsonObject result = restaurant_data.getJsonObject("result");
            restaurant.setRest_id(place_id);
            restaurant.setName(Functions.getCleanStringfromJSONString(result.getJsonString("name")));
            restaurant.setCuisine(null);
            restaurant.setOpeningHours(this.getFormattedOpeningHours(
                    result.getJsonObject("opening_hours").getJsonArray("periods")));
            restaurant.setAvailable_seats(0);
            restaurant.setAvg_price(0);
            restaurant.setAvg_rating(Functions.getJsonNumberToDouble(
                    (result.getJsonNumber("rating"))));
            restaurant.setAvg_waiting_time(0);
            restaurant.setOffers_seating_for_groups(false);
            restaurant.setOffers_preoder(false);
            restaurant.setPhonenumber(Functions.getCleanStringfromJSONString(
                    result.getJsonString("formatted_phone_number")));
            restaurant.setEmail("");
            restaurant.setWebsite(Functions.getCleanStringfromJSONString(
                    result.getJsonString("website")));
            restaurant.setAddress(Functions.getCleanStringfromJSONString(
                    result.getJsonString("formatted_address")));//
            restaurant.setLat(Functions.getJsonNumberToDouble(result.getJsonObject("geometry").
                    getJsonObject("location").getJsonNumber("lat")));
            restaurant.setLon(Functions.getJsonNumberToDouble(result.getJsonObject("geometry").
                    getJsonObject("location").getJsonNumber("lng")));

                System.out.println("\t"+result.getJsonString("website"));
                System.out.println("\t"+result.getJsonString("international_phone_number"));
                System.out.println(restaurant.getOpeningHours().get(0).getDay() + " "+
                        restaurant.getOpeningHours().get(0).getOpen() + " " +
                        restaurant.getOpeningHours().get(0).getClose());

        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + ex.toString());
        }
        return restaurant;
    }
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=&radius=&type=&keyword=&key=AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0

    //https://maps.googleapis.com/maps/api/place/details/json?placeid=&key=AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0

    //https://maps.googleapis.com/maps/api/place/textsearch/json?query=&key=AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0

    public String getFormattedUrlApiRequestRestaurant(String place_id){
        return this.urlprovider+"details/json?placeid="+place_id+"&key="+this.applicaton_key;
    }

    public String getFormattedUrlApiRequestSearch(){
        return this.urlprovider+"nearbysearch/json?"+this.getParameters().getFormattedParamsGoogle()+
                "&type="+type+"&key="+this.applicaton_key;
    }

    public ArrayList<OpeningHours> getFormattedOpeningHours(JsonArray openingHoursArray){
        ArrayList<OpeningHours> arrayOpeningHours = new ArrayList<OpeningHours>();
        OpeningHours openingHours = null;

            for (JsonObject result : openingHoursArray.getValuesAs(JsonObject.class) ){
                openingHours = new OpeningHours();
                openingHours.setDay(result.getJsonObject("close").getInt("day"));
                openingHours.setClose(Functions.getFormattedMilitarTimeToDateTime(result.getJsonObject("close").getString("time")));
                openingHours.setOpen(Functions.getFormattedMilitarTimeToDateTime(result.getJsonObject("open").getString("time")));
                arrayOpeningHours.add(openingHours);
            }

        return arrayOpeningHours;
    }

}
