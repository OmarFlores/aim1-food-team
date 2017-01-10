package tu.berlin.dima.food.classes;

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

    @Override
    public String toString() {
        return "radius=" + radius +
                "&ll=" + lat +
                "," + lon +
                "&query=" + cuisine;
    }

    public String getFormattedParamsGoogle(){
        return "radius=" + radius +
                "&location=" + lat +
                "," + lon +
                "&keyword=" + cuisine;
    }
}
