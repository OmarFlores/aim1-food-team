package tu.berlin.dima.food.test;

/**
 * Created by Jaguar on 1/9/17.
 */

import tu.berlin.dima.food.classes.Parameters;
import tu.berlin.dima.food.generation.Apirequestfoursquare;
import tu.berlin.dima.food.generation.Apirequestgoogleplaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import tu.berlin.dima.food.request.Requestapi;

public class TestApirequest {
    public static void main(String[] args) {

        Requestapi.getArrayListRestaurants("",52.4930605,13.4173319,1000);

        /*
        Apirequestgoogleplaces apirequestgoogleplaces = new Apirequestgoogleplaces();
        Apirequestfoursquare apirequestfoursquare = new Apirequestfoursquare();
        Parameters params = new Parameters();


        Properties prop = new Properties();
        InputStream input = null;

        params.setCuisine("pizza");
        params.setLat(52.5149719);
        params.setLon(13.3264126);
        params.setRadius(1000);

        apirequestgoogleplaces.setUrlprovider("https://maps.googleapis.com/maps/api/place/");
        apirequestgoogleplaces.setParameters(params);
        apirequestgoogleplaces.setApplicaton_key("AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0");
        apirequestgoogleplaces.getJSONStreamData();

        try {

            String filename = "config.properties";
            input = TestApirequest.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            System.out.println(prop.getProperty("googleplacesclientid"));
            System.out.println(prop.getProperty("foursquareclientid"));
            System.out.println(prop.getProperty("foursaquareclientsecretid"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

    }
}
