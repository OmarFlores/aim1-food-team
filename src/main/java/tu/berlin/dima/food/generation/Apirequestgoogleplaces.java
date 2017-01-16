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
        String pageToken="";
        System.out.println(this.getFormattedUrlApiRequestSearch());

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
                    System.out.println(result.getString("name"));
                    System.out.println(result.getJsonString("vicinity"));
                    System.out.println(Functions.getCleanStringfromJSONString(result.getJsonString("place_id")));
                    restaurants.add(this.getRestaurantData(
                            Functions.getCleanStringfromJSONString(result.getJsonString("place_id"))));
                    System.out.println("-----------");
                }
                System.out.println("NextPage-----------" + this.restaurants_data.getJsonString("next_page_token"));
            }while (this.restaurants_data.containsKey("next_page_token"));

        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + ex.toString());
        }
    }


    public Restaurant getRestaurantData(String place_id){
        InputStream inputStream;
        Restaurant restaurant = new Restaurant();
        System.out.println(this.getFormattedUrlApiRequestRestaurant(place_id));

        Apirequestfoursquare apirequestfoursquare = new Apirequestfoursquare();
        apirequestfoursquare.setUrlprovider("https://api.foursquare.com/v2/venues/");
        apirequestfoursquare.setPublic_client_key("HBDPIB5LCAKZ1BY1SEL5Z4OXLSSLLHJUVBZD2MTKTJ0GO4AO");
        apirequestfoursquare.setPrivate_client_key("5VXHH0FQR2GYBUORGRQ5TA51A1GPZSKM4MUGW33CVHB0GGHB");

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


            restaurant.setAvailable_seats(0);

            if (result.containsKey("rating"))
                restaurant.setAvg_rating(Functions.getJsonNumberToDouble(
                        (result.getJsonNumber("rating"))));
            else
                restaurant.setAvg_rating(0);

            restaurant.setAvg_waiting_time(0);
            restaurant.setOffers_seating_for_groups(false);
            restaurant.setOffers_preoder(false);

            if (result.containsKey("formatted_phone_number"))
                restaurant.setPhonenumber(Functions.getCleanStringfromJSONString(
                        result.getJsonString("formatted_phone_number")));
            else
                restaurant.setPhonenumber(null);

            restaurant.setEmail(null);

            if (result.containsKey("website"))
                restaurant.setWebsite(Functions.getCleanStringfromJSONString(
                        result.getJsonString("website")));
            else
                restaurant.setWebsite(null);

            if (result.containsKey("formatted_address"))
                restaurant.setAddress(Functions.getCleanStringfromJSONString(
                        result.getJsonString("formatted_address")));
            else
                restaurant.setAddress(null);

            restaurant.setLat(Functions.getJsonNumberToDouble(result.getJsonObject("geometry").
                    getJsonObject("location").getJsonNumber("lat")));
            restaurant.setLon(Functions.getJsonNumberToDouble(result.getJsonObject("geometry").
                    getJsonObject("location").getJsonNumber("lng")));

                System.out.println("\t"+result.getJsonString("website"));
                System.out.println("\t"+result.getJsonString("international_phone_number"));

            restaurant.setAvg_price(apirequestfoursquare.getJSONStreamDataForByLocationAndName(restaurant.getName(),
                    String.valueOf(restaurant.getLat()),String.valueOf(restaurant.getLon())));

            System.out.println("\t Price Rate"+restaurant.getAvg_price());

            if (restaurant.getOpeningHours().size() > 0)
                System.out.println(restaurant.getOpeningHours().toString());

        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + ex.toString());
        }catch (NullPointerException npex){
            logger.error("Error at Apirequestgoogleplaces.getRestaurantData: " + npex.toString());
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

    public String getFormattedUrlApiRequestSearchByPageToken(String pageToken){
        return this.urlprovider+"nearbysearch/json?"+this.getParameters().getFormattedParamsGoogle()+
                "&type="+type+"&key="+this.applicaton_key+"&pagetoken="+pageToken;
    }

    /*public ArrayList<OpeningHours> getFormattedOpeningHours(JsonArray openingHoursArray){
        ArrayList<OpeningHours> arrayOpeningHours = new ArrayList<OpeningHours>();
        OpeningHours openingHours = null;

        try{
            for (JsonObject result : openingHoursArray.getValuesAs(JsonObject.class) ){
                openingHours = new OpeningHours();
                openingHours.setDay(result.getJsonObject("close").getInt("day"));
                openingHours.setClose(Functions.getFormattedMilitarTimeToDateTime(result.getJsonObject("close").getString("time")));
                openingHours.setOpen(Functions.getFormattedMilitarTimeToDateTime(result.getJsonObject("open").getString("time")));
                arrayOpeningHours.add(openingHours);
            }
        }catch (Exception ex){
            logger.error("Error at Apirequestgoogleplaces.getFormattedOpeningHours: " + ex.getMessage());
            return arrayOpeningHours;
        }
        return arrayOpeningHours;
    }*/

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
