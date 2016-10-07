package com.kanishth.pat;

/**
 * Created by Kanishth on 9/10/2016.
 */

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Downloader {
    public static ArrayList<LatLng> getLatLong(String area)
    {
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=";
        String url2 = "&sensor=false";

        ArrayList<LatLng> values = new ArrayList<>();

        if(area==null) {
            System.out.println("null area Error!");
            return null;
        }
        else
        {
            try
            {
                String word="";
                for(int i=0; i<area.length();i++)
                {
                    if(area.charAt(i)!=',')
                    {
                        word +=area.charAt(i);
                        continue;
                    }

                    String value = read(url + word + url2);
                    //value = value.substring(3);
                    value = value.replace(" ", ""); // Remove blank spaces

                    JSONObject jsonObject = (JSONObject) new JSONTokener(value).nextValue();

                    String status = jsonObject.get("status").toString();

                    if (!status.equalsIgnoreCase("OK")) {
                        System.out.println("API Error!");
                        continue;
                    }
                    else {

                        JSONArray j_results = (JSONArray) jsonObject.get("results");
                        JSONObject j_geometry = (JSONObject) ((JSONObject) j_results.get(0)).get("geometry");
                        JSONObject j_location = (JSONObject) j_geometry.get("location");
                        Double lat = Double.parseDouble((j_location.get("lat")).toString());
                        Double lng = Double.parseDouble((j_location.get("lng")).toString());

                        values.add(new LatLng(lat,lng));
                    }
                    word="";
                }
                return values;
            }
            catch(Exception e)
            {
                System.err.println(e);
                return null;
            }

        }
    }
    public static String read(String urlString)
    {
        String data="";
        try
        {
            URL url = new URL(urlString);
            URLConnection urlC = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                data += inputLine;
            in.close();
        }
        catch(Exception e)
        {System.out.println("ERROR: read: "+e);}

        return data;
    }
}
