package tu.berlin.dima.food.request;

/**
 * Created by Jaguar on 1/12/17.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tu.berlin.dima.food.classes.Parameters;
import tu.berlin.dima.food.classes.Restaurant;
import tu.berlin.dima.food.generation.Apirequestgoogleplaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Requestapi {

    public static String foursquare_url_api;
    public static String foursquare_client_id;
    public static String foursquare_client_secret_id;
    public static String googleapi_url_api;
    public static String googleapi_client__id;
    public static int default_radius;

    final static Logger logger = LogManager.getLogger(Requestapi.class);

    public static ArrayList<Restaurant> getArrayListRestaurants(String keyword,double lat, double lon,int radius){
        Apirequestgoogleplaces apirequestgoogleplaces = new Apirequestgoogleplaces();
        Parameters params = new Parameters();
        ArrayList<Restaurant> arrayListRestaurants = new ArrayList<Restaurant>();
        try{

            readConfigParameters();
            params.setCuisine(keyword);
            params.setLat(lat);
            params.setLon(lon);
            if (radius > 0)
                params.setRadius(10000);
            else
                params.setRadius(default_radius);

            apirequestgoogleplaces.setUrlprovider(googleapi_url_api);
            apirequestgoogleplaces.setParameters(params);
            apirequestgoogleplaces.setApplicaton_key(googleapi_client__id);
            apirequestgoogleplaces.getJSONStreamData();
            arrayListRestaurants =  apirequestgoogleplaces.getRestaurants();

        }catch (Exception e){
            logger.error("Error at Requestapi.getArrayListRestaurants: " + e.toString());
        }

        return arrayListRestaurants;
    }

    public static void readConfigParameters(){

        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "config.properties";
            input = Requestapi.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            prop.load(input);
            foursquare_url_api = prop.getProperty("foursquareapiurl");
            foursquare_client_id = prop.getProperty("foursquareclientid");
            foursquare_client_secret_id = prop.getProperty("foursaquareclientsecretid");
            googleapi_url_api = prop.getProperty("googleapiurl");
            googleapi_client__id = prop.getProperty("googleplacesclientid");
            default_radius = Integer.parseInt(prop.getProperty("radius"));

        } catch (IOException ex) {
            logger.error("Error at Requestapi.readConfigParameters: " + ex.toString());
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("Error at Requestapi.readConfigParameters: " + e.toString());
                }
            }
        }

    }
}
