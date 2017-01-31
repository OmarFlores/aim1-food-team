package tu.berlin.dima.food.classes;

import java.util.Date;

/**
 * Created by Jaguar on 1/12/17.
 * OpeningHours class is used for getting the Opening hours of the restaurants
 */

public class OpeningHours {
    /**
     * Day of the week
     */
    private int day;
    /**
     * Restaurant Opening time in hours
     */
    private String open;
    /**
     * Restaurant Closing time in hours
     */
    private String close;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    /**
     * Re-implementation of toString method to print all members of the object OpeningHours.
     * @return string of day, open and close hours of the restaurant
     */
    @Override
    public String toString() {
        return "Day " + day
                +" Open " + open
                +" Close "+ close;
    }
}
