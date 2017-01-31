package tu.berlin.dima.food.generation;

/**
 * Created by Jaguar on 1/10/17.
 */

import javax.json.*;
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
import tu.berlin.dima.food.request.Requestapi;
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

    /**
     * Constructor to initialize parameters to connect to googleplaces API
     * @param urlprovider url for googleplaces API
     * @param applicaton_key key for querying the googleplaces API
     * @param connection the connection object to connect to the googleplaces API
     * @param parameters the objects containing all the parameters for query googleplaces API
     */
    public Apirequestgoogleplaces(String urlprovider, String applicaton_key, URL connection, Parameters parameters) {
        this.urlprovider = urlprovider;
        this.applicaton_key = applicaton_key;
        this.connection = connection;
        this.parameters = parameters;
        restaurants_data = null;
        restaurant_data= null;
        restaurants = new ArrayList<Restaurant>();
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
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

    /**
     * Sets the restaurant data in the JSON object
     * @param restaurants_data
     */
    public void setRestaurants_data(JsonObject restaurants_data) {
        this.restaurants_data = restaurants_data;
    }

    /**
     * Returns a Restaurant object with all the information about the restaurant(Restaurant name, Address, Location, Website,
     * contact, price rate and rate).
     */
    public void getJSONStreamData(){
        InputStream is;
        String pageToken="";

        try {
            do{
                if (pageToken.equals(""))
                    this.connection = new URL(this.getFormattedUrlApiRequestSearch());
                else
                    this.connection = new URL(this.getFormattedUrlApiRequestSearchByPageToken(pageToken));

                is = connection.openStream();
                JsonReader reader = Json.createReader(is);
                this.restaurants_data = reader.readObject();

                if (this.restaurants_data.containsKey("next_page_token"))
                    pageToken=this.restaurants_data.getString("next_page_token");

                JsonArray results = restaurants_data.getJsonArray("results");
                for (JsonObject result : results.getValuesAs(JsonObject.class) ){

                    Restaurant restaurantObject = this.getRestaurantData(
                            Functions.getCleanStringfromJSONString(result.getJsonString("place_id")));
                    restaurants.add(restaurantObject);

                    System.out.println("Restaurant " + restaurantObject.getName());
                    System.out.println("Address " + restaurantObject.getAddress());
                    System.out.println("Lat " + restaurantObject.getLat());
                    System.out.println("Lon " + restaurantObject.getLon());
                    System.out.println("\t WebPage"+restaurantObject.getWebpage());
                    System.out.println("\t Phone "+restaurantObject.getPhone());
                    System.out.println("\t Price Rate "+restaurantObject.getPrice());
                    System.out.println("\t Rate "+restaurantObject.getRating());

                    if (restaurantObject.getOpeningHours().size() > 0)
                        System.out.println(restaurantObject.getOpeningHours().toString());

                    System.out.println("-----------");
                }
            }while (this.restaurants_data.containsKey("next_page_token"));

        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + ex.toString());
        }
    }


    /**
     * Requests to get the restaurant Data according to place id.
     * @param place_id
     * @return Restaurant Data
     */
    public Restaurant getRestaurantData(String place_id){
        InputStream inputStream;
        Restaurant restaurant = new Restaurant();

        Apirequestfoursquare apirequestfoursquare = new Apirequestfoursquare();
        apirequestfoursquare.setUrlprovider(Requestapi.foursquare_url_api);
        apirequestfoursquare.setPublic_client_key(Requestapi.foursquare_client_id);
        apirequestfoursquare.setPrivate_client_key(Requestapi.foursquare_client_secret_id);

        try {
            URL connection = new URL(this.getFormattedUrlApiRequestRestaurant(place_id));
            inputStream = connection.openStream();

            JsonReader reader = Json.createReader(inputStream);
            this.restaurant_data = reader.readObject();

            JsonObject result = restaurant_data.getJsonObject("result");
            restaurant.setRest_id(place_id);
            restaurant.setName(Functions.getCleanStringfromJSONString(result.getJsonString("name")));
            restaurant.setCuisine(null);

            if (result.containsKey("opening_hours"))
                restaurant.setOpeningHours(this.getFormattedOpeningHours(
                        result.getJsonObject("opening_hours")));

            if (result.containsKey("rating"))
                restaurant.setRating(Functions.getJsonNumberToDouble(
                        (result.getJsonNumber("rating"))));
            else
                restaurant.setRating(0);

            if (result.containsKey("international_phone_number"))
                restaurant.setPhone(Functions.getCleanStringfromJSONString(
                        result.getJsonString("international_phone_number")));
            else
                restaurant.setPhone(null);

            if (result.containsKey("website"))
                restaurant.setWebpage(Functions.getCleanStringfromJSONString(
                        result.getJsonString("website")));
            else
                restaurant.setWebpage(null);

            if (result.containsKey("formatted_address"))
                restaurant.setAddress(Functions.getCleanStringfromJSONString(
                        result.getJsonString("formatted_address")));
            else
                restaurant.setAddress(null);

            restaurant.setLat(Functions.getJsonNumberToDouble(result.getJsonObject("geometry").
                    getJsonObject("location").getJsonNumber("lat")));
            restaurant.setLon(Functions.getJsonNumberToDouble(result.getJsonObject("geometry").
                    getJsonObject("location").getJsonNumber("lng")));

            restaurant.setPrice(apirequestfoursquare.getJSONStreamDataForByLocationAndName(restaurant.getName(),
                    String.valueOf(restaurant.getLat()),String.valueOf(restaurant.getLon())));

        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + e.getMessage());
        }catch (IOException ex){
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + ex.getMessage());
        }catch (NullPointerException npex){
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + npex.getMessage());
        }
        return restaurant;
    }

    /**
     * requests the formatted URL of the query to request the API with a Place Id as parameter.
     * @param place_id
     * @return URL of the API query.
     */
    public String getFormattedUrlApiRequestRestaurant(String place_id){
        return this.urlprovider+"details/json?placeid="+place_id+"&key="+this.applicaton_key;
    }

    /**
     * requests the formatted URL of the query to request the API.
     * @return URL of the API query.
     */
    public String getFormattedUrlApiRequestSearch(){
        return this.urlprovider+"nearbysearch/json?"+this.getParameters().getFormattedParamsGoogle()+
                "&type="+type+"&key="+this.applicaton_key;
    }

    /**
     * requests the formatted URL of the query to request the API with a Page Token as parameter.
     * @param pageToken
     * @return URL of the API query.
     */
    public String getFormattedUrlApiRequestSearchByPageToken(String pageToken){
        return this.urlprovider+"nearbysearch/json?"+this.getParameters().getFormattedParamsGoogle()+
                "&type="+type+"&key="+this.applicaton_key+"&pagetoken="+pageToken;
    }

    /**
     * requests the opening hours of the restaurant with the opening hours JSON object.
     * @param openingHoursObject
     * @return opening hours of the restaurant in form of an array.
     */
    public ArrayList<OpeningHours> getFormattedOpeningHours(JsonObject openingHoursObject){
        ArrayList<OpeningHours> arrayOpeningHours = new ArrayList<OpeningHours>();
        OpeningHours openingHours = null;

        try{
            if (openingHoursObject.containsKey("periods")) {
                for (JsonObject result : openingHoursObject.getJsonArray("periods").getValuesAs(JsonObject.class)) {
                    openingHours = new OpeningHours();

                    openingHours.setDay(result.getJsonObject("close").getInt("day"));
                    openingHours.setClose(Functions.getFormattedMilitarTimeToDateTime(result.getJsonObject("close").getString("time")));
                    openingHours.setOpen(Functions.getFormattedMilitarTimeToDateTime(result.getJsonObject("open").getString("time")));
                    arrayOpeningHours.add(openingHours);
                }
            }
        }catch (Exception ex){
            logger.error("Error at Apirequestgoogleplaces.getFormattedOpeningHours: " +ex.getMessage());
        }
        return arrayOpeningHours;
    }

}
