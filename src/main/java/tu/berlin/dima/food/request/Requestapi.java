package tu.berlin.dima.food.request;

/**
 * Created by Jaguar on 1/12/17.
 */
import tu.berlin.dima.food.classes.Parameters;
import tu.berlin.dima.food.generation.Apirequestgoogleplaces;

public class Requestapi {
    public static void main(String[] args) {

        Apirequestgoogleplaces apirequestgoogleplaces = new Apirequestgoogleplaces();
        Parameters params = new Parameters();

        params.setCuisine("pizza");
        params.setLat(52.5149719);
        params.setLon(13.3264126);
        params.setRadius(10000);

        apirequestgoogleplaces.setUrlprovider("https://maps.googleapis.com/maps/api/place/");
        apirequestgoogleplaces.setParameters(params);
        apirequestgoogleplaces.setApplicaton_key("AIzaSyAlQVj6HynxNo0JcEg0_3clzXMwReRGsL0");
        apirequestgoogleplaces.getJSONStreamData();



    }
}
