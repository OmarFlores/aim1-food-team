package tu.berlin.dima.food.classes;

import java.util.Date;

/**
 * Created by Jaguar on 1/12/17.
 */
public class OpeningHours {
    private int day;
    private String open;
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
}
