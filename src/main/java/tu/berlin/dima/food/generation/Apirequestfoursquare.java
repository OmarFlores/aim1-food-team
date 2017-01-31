package tu.berlin.dima.food.generation;

/**
 * Created by Jaguar on 1/6/17.
 * Apirequestfoursquare class is used to send the foursquare API the query to retrieve restaurant information.
 */


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import tu.berlin.dima.food.classes.Parameters;


public class Apirequestfoursquare {

    private String urlprovider;
    private String public_client_key;
    private String private_client_key;
    private URL connection;
    private Parameters parameters;
    private JsonObject restaurants_data;

    final static Logger logger = LogManager.getLogger(Apirequestfoursquare.class);
    final static String categoryId = "4d4b7105d754a06374d81259";

    /**
     * Default Apirequestfoursquare constructor
     */
    public Apirequestfoursquare(){}

    /**
     * Constructor to initialize Apirequestfoursquare object
     * @param urlprovider url for foursquare API
     * @param public_client_key client id for requesting a query to foursquare API
     * @param private_client_key client secret key for querying the foursquare API
     */
    public Apirequestfoursquare(String urlprovider, String public_client_key, String private_client_key){
        this.urlprovider = urlprovider;
        this.public_client_key = public_client_key;
        this.private_client_key = private_client_key;
        this.connection = null;
        this.restaurants_data = null;
    }

    /**
     * get URL of foursquare
     * @return URL of the foursquare API
     */
    public String getUrlprovider() {
        return urlprovider;
    }

    /**
     * Set the URL of foursqure API
     * @param urlprovider
     */
    public void setUrlprovider(String urlprovider) {
        this.urlprovider = urlprovider;
    }

    public URL getConnection() {
        return connection;
    }

    public void setConnection(URL connection) {
        this.connection = connection;
    }

    public JsonObject getRestaurants_data() {
        return restaurants_data;
    }

    public void setRestaurants_data(JsonObject restaurants_data) {
        this.restaurants_data = restaurants_data;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    /**
     * get the client id for requesting a query to foursquare API
     * @return
     */
    public String getPublic_client_key() {
        return public_client_key;
    }

    /**
     * set the client id
     * @param public_client_key
     */
    public void setPublic_client_key(String public_client_key) {
        this.public_client_key = public_client_key;
    }

    /**
     * get the client secret key for querying the foursquare API
     * @return
     */
    public String getPrivate_client_key() {
        return private_client_key;
    }

    /**
     * set the client secret key
     * @param private_client_key
     */
    public void setPrivate_client_key(String private_client_key) {
        this.private_client_key = private_client_key;
    }


    /**
     * reading the restaurant data from the JSON object by location and name of the cuisine
     * @param name name of the cuisine
     * @param lat latitude of the location
     * @param lon longitude of the location
     * @return price rating
     */
    public int getJSONStreamDataForByLocationAndName(String name,String lat, String lon){
        InputStream is;
        int priceRating = 0;

        try {
            this.connection = new URL(this.getFormattedUrlSearchByNameAndLocation(name,lat,lon));
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonArray results = restaurants_data.getJsonObject("response").getJsonArray("venues");

            for (JsonObject result : results.getValuesAs(JsonObject.class) ){
                priceRating = this.getJSONRestaurantPrice(name,
                        (result.getJsonString("id").toString().replace("\"","")));
            }
        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestfoursquare.getJSONStreamDataForByLocationAndName: " + e.toString());
            return priceRating;
        }catch (IOException ex){
            logger.error("Error at Apirequestfoursquare.getJSONStreamDataForByLocationAndName: " + ex.toString());
            return priceRating;
        }
        return priceRating;
    }


    /**
     * requesting the price rate of a restaurant taking the name of restaurant and venue id as parameters
     * @param nameRestaurant name of the restaurant
     * @param id_venue unique id of the venue for the foursquare API
     * @return price rate (price rate: 0 - No rating available, 1 - Inexpensive , 2- moderate , 3- expensive, 4 - very expensive)
     */
    public int getJSONRestaurantPrice(String nameRestaurant,String id_venue){
        InputStream is;
        int priceRate = 0;
        try {
            this.connection = new URL(this.getFormattedUrlApiRequestRestaurant(id_venue));
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonObject result = restaurants_data.getJsonObject("response").getJsonObject("venue");
                priceRate = result.getJsonObject("price").getInt("tier");

        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestfoursquare.getJSONRestaurantPrice: " + e.toString());
            return priceRate;
        }catch (IOException ex){
            logger.error("Error at Apirequestfoursquare.getJSONRestaurantPrice: " + ex.toString());
            return priceRate;
        }catch (NullPointerException npe){
            return priceRate;
        }
        return priceRate;
    }

    /**
     * retrieving the client id and client secret key
     * @return client id and client secret key
     */
    public String getFormattedUserApiKey(){
        return "client_id="+this.getPublic_client_key()+"&client_secret="+this.getPrivate_client_key();
    }

    /**
     * requesting the URL for querying the foursquare API by name of the cuisine, latitude and longitude
     * @param name name of the cuisine
     * @param lat latitude of the location
     * @param lon longitude of the location
     * @return URL in correct format to query the foursquare API
     * @throws UnsupportedEncodingException
     */
    public String getFormattedUrlSearchByNameAndLocation(String name, String lat,String lon) throws UnsupportedEncodingException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return this.urlprovider+"search?intent=match&ll="+lat+","+lon+"&query="+ URLEncoder.encode(name.replace(" ","%20"), "UTF-8")+
                "&categoryId="+categoryId+"&"+this.getFormattedUserApiKey()+"&v="+strDate;
    }

    /**
     * requesting the URL of foursquare API by id of the venue
     * @param id_venue unique id of the venue for the foursquare API
     * @return URL in correct format to query the foursquare API
     */
    public String getFormattedUrlApiRequestRestaurant(String id_venue){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return this.urlprovider+id_venue+"?"+this.getFormattedUserApiKey()+"&v="+strDate;
    }
}