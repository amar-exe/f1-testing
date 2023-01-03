package ba.edu.ibu;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
}
