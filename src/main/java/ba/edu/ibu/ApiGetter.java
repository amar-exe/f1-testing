package ba.edu.ibu;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;

public class ApiGetter {


    public static DriverModel getDrivers2022() {
        System.out.println("Getting drivers information and stats...");
        DriverModel driverModel = null;
        try {
            URL url = new URL("http://ergast.com/api/f1/2022/driverStandings.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");


            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            Gson gson = new Gson();
            driverModel = gson.fromJson(String.valueOf(response.substring(10, response.length()-1)), DriverModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driverModel;
    }

    public static String getCurrentTimeInSakhir(){
        System.out.println("Getting current time in Sakhir...");
        String currentTime ="";
        try {
            URL timeApiUrl = new URL("http://worldtimeapi.org/api/timezone/Asia/Bahrain");

            BufferedReader in = new BufferedReader(new InputStreamReader(timeApiUrl.openStream()));
            String apiResponse = in.readLine();
            in.close();
            Gson gson = new Gson();
            TimeZoneModel timezoneInfo = gson.fromJson(apiResponse, TimeZoneModel.class);
            currentTime=timezoneInfo.datetime;
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(currentTime);
            int hours = zonedDateTime.getHour();
            int minutes = zonedDateTime.getMinute();
            currentTime=String.format("%02d", hours)+":"+String.format("%02d", minutes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentTime;

    }

}
