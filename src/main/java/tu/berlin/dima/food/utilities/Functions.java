package tu.berlin.dima.food.utilities;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jaguar on 1/12/17.
 */
public class Functions {
    public static String getFormattedMilitarTimeToDateTime(String militarTime){

        DateFormat input = new SimpleDateFormat("HHmm");
        DateFormat output = new SimpleDateFormat("HH:mm");
        Date date = null;
        String timeStamp = "";
        try {
            date = input.parse(militarTime);
            timeStamp = output.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeStamp;
    }

    public static String getCleanStringfromJSONString(JsonString jsonString){
        return (jsonString != null ) ? jsonString.toString().replaceAll("\"","") : null;
    }

    public static double getJsonNumberToDouble(JsonNumber jsonNumber){
        return (jsonNumber != null) ? Double.parseDouble(jsonNumber.toString()) : 0.0;
    }

    public static boolean jsonObjectHasKey(JsonObject jsonObject, String keyName){

        boolean val = false;
        try {
            val = jsonObject.isNull(keyName);
        }catch (Exception e){

            return val;
        }
        return val;
    }

}
