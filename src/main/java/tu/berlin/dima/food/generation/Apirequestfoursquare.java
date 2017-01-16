package tu.berlin.dima.food.generation;

/**
 * Created by Jaguar on 1/6/17.
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
import tu.berlin.dima.food.classes.Restaurant;
import tu.berlin.dima.food.utilities.Functions;

public class Apirequestfoursquare {

    private String urlprovider;
    private String public_client_key;
    private String private_client_key;
    private URL connection;
    private Parameters parameters;
    private JsonObject restaurants_data;

    final static Logger logger = LogManager.getLogger(Apirequestfoursquare.class);
    final static String categoryId = "4d4b7105d754a06374d81259";


    public Apirequestfoursquare(){}

    public Apirequestfoursquare(String urlprovider, String public_client_key, String private_client_key){
        this.urlprovider = urlprovider;
        this.public_client_key = public_client_key;
        this.private_client_key = private_client_key;
        this.connection = null;
        this.restaurants_data = null;
    }

    public String getUrlprovider() {
        return urlprovider;
    }

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

    public String getPublic_client_key() {
        return public_client_key;
    }

    public void setPublic_client_key(String public_client_key) {
        this.public_client_key = public_client_key;
    }

    public String getPrivate_client_key() {
        return private_client_key;
    }

    public void setPrivate_client_key(String private_client_key) {
        this.private_client_key = private_client_key;
    }

    public void getJSONStreamData(){
        InputStream is;
        System.out.println(this.getFormattedUrlApiRequestSearch());
        try {
            this.connection = new URL(this.getFormattedUrlApiRequestSearch());
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonArray results = restaurants_data.getJsonObject("response").getJsonArray("venues");
            for (JsonObject result : results.getValuesAs(JsonObject.class) ){
                System.out.println(result.getString("name"));
                System.out.println(result.getJsonObject("location").getJsonString("address"));
                System.out.println("-----------");
            }
        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestfoursquare.getJSONStreamData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestfoursquare.getJSONStreamData: " + ex.toString());
        }
    }

    //https://api.foursquare.com/v2/venues/search?
    // intent=match&
    // ll=52.5169226,13.3090521&
    // query=Trattoria%20Rathaus%20Piazza&
    // categoryId=4d4b7105d754a06374d81259&
    // client_id=HBDPIB5LCAKZ1BY1SEL5Z4OXLSSLLHJUVBZD2MTKTJ0GO4AO&
    // client_secret=5VXHH0FQR2GYBUORGRQ5TA51A1GPZSKM4MUGW33CVHB0GGHB&
    // v=20170115

    public int getJSONStreamDataForByLocationAndName(String name,String lat, String lon){
        InputStream is;
        int priceRating = 0;
        //System.out.println(this.getFormattedUrlSearchByNameAndLocation(name,lat,lon));
        try {
            this.connection = new URL(this.getFormattedUrlSearchByNameAndLocation(name,lat,lon));
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonArray results = restaurants_data.getJsonObject("response").getJsonArray("venues");

            for (JsonObject result : results.getValuesAs(JsonObject.class) ){
                //System.out.println(result.getJsonString("id").toString().replace("\"",""));
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

    //https://api.foursquare.com/v2/venues/53ab0bdd498ef2b98039f0d0?
    // client_id=HBDPIB5LCAKZ1BY1SEL5Z4OXLSSLLHJUVBZD2MTKTJ0GO4AO&
    // client_secret=5VXHH0FQR2GYBUORGRQ5TA51A1GPZSKM4MUGW33CVHB0GGHB&v=20170109

    public Restaurant getJSONRestaurantDetailData(String nombre,String id_venue){
        InputStream is;
        Restaurant restaurant = new Restaurant();
        System.out.println(this.getFormattedUrlApiRequestRestaurant(id_venue));
        try {
            this.connection = new URL(this.getFormattedUrlApiRequestRestaurant(id_venue));
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonArray results = restaurants_data.getJsonObject("response").getJsonArray("venues");
            for (JsonObject result : results.getValuesAs(JsonObject.class) ){
                System.out.println(result.getString("name"));
                System.out.println(result.getJsonObject("location").getJsonString("address"));
                System.out.println("-----------");
            }
        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestfoursquare.getJSONStreamData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestfoursquare.getJSONStreamData: " + ex.toString());
        }
        return restaurant;
    }

    public int getJSONRestaurantPrice(String nombre,String id_venue){
        InputStream is;
        int priceRate = 0;
        //System.out.println(this.getFormattedUrlApiRequestRestaurant(id_venue));
        try {
            this.connection = new URL(this.getFormattedUrlApiRequestRestaurant(id_venue));
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonObject result = restaurants_data.getJsonObject("response").getJsonObject("venue");
                //System.out.println("Price Rating "+result.getJsonObject("price").getInt("tier"));
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

    public String getFormattedUserApiKey(){
        return "client_id="+this.getPublic_client_key()+"&client_secret="+this.getPrivate_client_key();
    }

    public String getFormattedUrlApiRequestSearch(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return this.urlprovider+"search?"+this.getParameters().toString()+
                "&categoryId="+categoryId+"&"+this.getFormattedUserApiKey()+"&v="+strDate;
    }

    public String getFormattedUrlSearchByNameAndLocation(String name, String lat,String lon) throws UnsupportedEncodingException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return this.urlprovider+"search?intent=match&ll="+lat+","+lon+"&query="+ URLEncoder.encode(name.replace(" ","%20"), "UTF-8")+
                "&categoryId="+categoryId+"&"+this.getFormattedUserApiKey()+"&v="+strDate;
    }

    public String getFormattedUrlApiRequestRestaurant(String id_venue){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return this.urlprovider+id_venue+"?"+this.getFormattedUserApiKey()+"&v="+strDate;
    }
}