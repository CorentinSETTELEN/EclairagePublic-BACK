package com.example.apirest;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenDataApi {

    static JSONArray callOpenDataParisApi(String url_string)
    {
        JSONArray output = new JSONArray();

        try {
            URL url = new URL(url_string);

            // URL url = new URL("https://opendata.paris.fr/api/records/1.0/search/?dataset=eclairage-public&facet=ville&refine.ville=" + dataset);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String line;
            while ((line = br.readLine()) != null) {
                output.put(line);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }


    public static JSONArray callApi(String string_url) throws JSONException {
        JSONArray outputs = OpenDataApi.callOpenDataParisApi(string_url);
        int length = outputs.length();
        JSONArray response = new JSONArray();

        for (int i=0;i<length;i++){
            String element = outputs.getString(i);
            response = OpenDataApi.jsonToObject(element);
        }
        return response;
    }

    /*
    List all StreetLights
     */
    public static JSONArray jsonToObject(String jsonString) throws JSONException {
        JSONObject jsonDecode = new JSONObject(jsonString);
        JSONArray jsonDecodeRecords = jsonDecode.getJSONArray("records");

        return jsonDecodeRecords;
    }

}
