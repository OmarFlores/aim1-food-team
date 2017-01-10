package tu.berlin.dima.food.classes;

/**
 * Created by Jaguar on 1/6/17.
 */

import java.util.ArrayList;
import java.util.Date;

/**
    "restaurant":"alphanumeric",
"cuisine":"alphanumeric",
"lat":"numeric",
"lon":"numeric",
"address":"alphanumeric",
"openinghours":"alphanumeric",
"pricerange":"numeric",
"phonenumber":"numeric",
"email":"alphanumeric",
"website":"alphanumeric"

    * */

public class Restaurant {

    private int rest_id;
    private String name;
    private ArrayList<String> cuisine;
    private Date opentime;
    private Date closetime;
    private int available_seats;
    private double avg_price;
    private double avg_rating;
    private double avg_waiting_time;
    private boolean offers_seating_for_groups;
    private boolean offers_preoder;
    private String phonenumber;
    private String email;
    private String website;
    private double lat;
    private double lon;
    private String address;

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
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

    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    public Date getClosetime() {
        return closetime;
    }

    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    public int getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(int available_seats) {
        this.available_seats = available_seats;
    }

    public double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public double getAvg_waiting_time() {
        return avg_waiting_time;
    }

    public void setAvg_waiting_time(double avg_waiting_time) {
        this.avg_waiting_time = avg_waiting_time;
    }

    public boolean isOffers_seating_for_groups() {
        return offers_seating_for_groups;
    }

    public void setOffers_seating_for_groups(boolean offers_seating_for_groups) {
        this.offers_seating_for_groups = offers_seating_for_groups;
    }

    public boolean isOffers_preoder() {
        return offers_preoder;
    }

    public void setOffers_preoder(boolean offers_preoder) {
        this.offers_preoder = offers_preoder;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
