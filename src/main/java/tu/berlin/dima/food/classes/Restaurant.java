package tu.berlin.dima.food.classes;

/**
 * Created by Jaguar on 1/6/17.
 */

import java.util.ArrayList;

public class Restaurant {

    private String rest_id;
    private String name;
    private ArrayList<OpeningHours> openingHours;
    private ArrayList<String> cuisine;
    private double price;
    private double rating;
    private String phone;
    private String webpage;
    private double lat;
    private double lon;
    private String address;

    public Restaurant(){
        this.cuisine = new ArrayList<String>();
        this.openingHours = new ArrayList<OpeningHours>();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(ArrayList<String> cuisine) {
        this.cuisine = cuisine;
    }

    public ArrayList<OpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(ArrayList<OpeningHours> openingHours) {
        this.openingHours = openingHours;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
