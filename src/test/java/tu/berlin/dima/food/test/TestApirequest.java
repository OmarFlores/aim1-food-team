package tu.berlin.dima.food.test;

/**
 * Created by Jaguar on 1/9/17.
 */

import tu.berlin.dima.food.classes.Parameters;
import tu.berlin.dima.food.generation.Apirequestfoursquare;
import tu.berlin.dima.food.generation.Apirequestgoogleplaces;
//https://api.foursquare.com/v2/venues/search?ll=52.5149719,13.3264126&radius=1000&categoryId=4d4b7105d754a06374d81259&query=donuts&client_id=HBDPIB5LCAKZ1BY1SEL5Z4OXLSSLLHJUVBZD2MTKTJ0GO4AO&client_secret=5VXHH0FQR2GYBUORGRQ5TA51A1GPZSKM4MUGW33CVHB0GGHB&v=20170109

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.5153073,13.3267801&radius=1000&type=restaurant&keyword=pizza&key=AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0


public class TestApirequest {
    public static void main(String[] args) {
        Apirequestgoogleplaces apirequestgoogleplaces = new Apirequestgoogleplaces();
        Apirequestfoursquare apirequestfoursquare = new Apirequestfoursquare();
        Parameters params = new Parameters();

        params.setCuisine("pizza");
        params.setLat(52.5149719);
        params.setLon(13.3264126);
        params.setRadius(3000);


        apirequestgoogleplaces.setUrlprovider("https://maps.googleapis.com/maps/api/place/");
        apirequestgoogleplaces.setParameters(params);
        apirequestgoogleplaces.setApplicaton_key("AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0");
        apirequestgoogleplaces.getJSONStreamData();

        /*
        apirequestfoursquare.setUrlprovider("https://api.foursquare.com/v2/venues/");
        apirequestfoursquare.setParameters(params);
        apirequestfoursquare.setPublic_client_key("HBDPIB5LCAKZ1BY1SEL5Z4OXLSSLLHJUVBZD2MTKTJ0GO4AO");
        apirequestfoursquare.setPrivate_client_key("5VXHH0FQR2GYBUORGRQ5TA51A1GPZSKM4MUGW33CVHB0GGHB");
        apirequestfoursquare.getJSONStreamData();
        */



    }
}
