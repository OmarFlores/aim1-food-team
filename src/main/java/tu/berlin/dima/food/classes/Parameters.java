package tu.berlin.dima.food.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jaguar on 1/9/17.
 */
public class Parameters {
    private int radius;
    private double lat;
    private double lon;
    private String cuisine;
    private double pricerange;
    private double raterange;

    final static Logger logger = LogManager.getLogger(Parameters.class);

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getPricerange() {
        return pricerange;
    }

    public void setPricerange(double pricerange) {
        this.pricerange = pricerange;
    }

    public double getRaterange() {
        return raterange;
    }

    public void setRaterange(double raterange) {
        this.raterange = raterange;
    }

    /**
     * Re-implementation of a toString method to format parameters url to query foursquare api.
     * @return string formatted for foursquare api.
     */
    @Override

    public String toString() {
        try {
            return "radius=" + radius +
                    "&ll=" + lat +
                    "," + lon +
                    "&query=" + URLEncoder.encode(cuisine.replace(" ","%20"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error at Parameters.toString: " +e.getMessage());
        }
        return "";
    }

    /**
     * Method which retrieves a formatted parameters url for google places api.
     * @return string formatted for google places api.
     */
    public String getFormattedParamsGoogle() {
        try {
            return "radius=" + radius +
                    "&location=" + lat +
                    "," + lon +
                    "&keyword=" + URLEncoder.encode(cuisine.replace(" ","%20"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error at Parameters.getFormattedParamsGoogle: " +e.getMessage());
        }
        return "";
    }
}
