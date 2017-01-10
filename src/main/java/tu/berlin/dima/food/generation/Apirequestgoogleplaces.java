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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import tu.berlin.dima.food.classes.Parameters;

public class Apirequestgoogleplaces {

    private String urlprovider;
    private String applicaton_key;
    private URL connection;
    private Parameters parameters;
    private JsonObject restaurants_data;

    final static Logger logger = LogManager.getLogger(Apirequestgoogleplaces.class);
    final static String type = "restaurant";

    public Apirequestgoogleplaces(){}

    public Apirequestgoogleplaces(String provider, String urlprovider, String applicaton_key, URL connection, Parameters parameters) {
        this.urlprovider = urlprovider;
        this.applicaton_key = applicaton_key;
        this.connection = connection;
        this.parameters = parameters;
        restaurants_data = null;
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
        System.out.println(this.getFormattedUrlApiRequestSearch());
        try {
            this.connection = new URL(this.getFormattedUrlApiRequestSearch());
            is = connection.openStream();
            JsonReader reader = Json.createReader(is);
            this.restaurants_data = reader.readObject();
            JsonArray results = restaurants_data.getJsonArray("results");
            for (JsonObject result : results.getValuesAs(JsonObject.class) ){
                System.out.println(result.getString("name"));
                System.out.println(result.getJsonString("vicinity"));
                System.out.println("-----------");
            }
        } catch (MalformedURLException e) {
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + e.toString());
        }catch (IOException ex){
            logger.error("Error at Apirequestgoogleplaces.getJSONStreamData: " + ex.toString());
        }
    }
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.5153073,13.3267801&radius=1000&type=restaurant&keyword=pizza&key=AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0

    public String getFormattedUrlApiRequestSearch(){
        return this.urlprovider+"/json?"+this.getParameters().getFormattedParamsGoogle()+
                "&type="+type+"&key="+this.applicaton_key;
    }

}
