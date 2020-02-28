package com.example.apirest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreetLightController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/eclairage-public")
    public String streetLight(@RequestParam(value = "name", defaultValue = "World") String name) throws JSONException {
        // return new StreetLight(counter.incrementAndGet(), "", String.format(template, name));

        return StreetLightController.callOpenDataParisApi("eclairage-public").toString();

    }

    
    static JSONArray callOpenDataParisApi(String dataset)
    {
        JSONArray output = new JSONArray();

        try {

            URL url = new URL("https://opendata.paris.fr/api/records/1.0/search/?dataset=" + dataset);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            // output = br.readLine() != null ? StreetLightController.jsonToObject(br.readLine()) : new ArrayList<JSONObject>();
            String line;
            while ((line = br.readLine()) != null) {
                output = StreetLightController.jsonToObject(line);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // System.out.println(output);
        return output;
    }

    public static JSONArray jsonToObject(String jsonString) throws JSONException {
        JSONObject jsonDecode = new JSONObject(jsonString);
        // System.out.println(jsonDecode);

        JSONArray jsonDecodeRecords = jsonDecode.getJSONArray("records");

        int length = jsonDecodeRecords.length();
        // System.out.println(length);

        JSONArray listStreetLight = new JSONArray();
        for (int i=0;i<length;i++){
            JSONObject element = jsonDecodeRecords.getJSONObject(i);
            StreetLight streetLightElement = new StreetLight(
                    i +1,
                    element.getString("recordid").trim(),
                    element.getString("datasetid")
            );

            listStreetLight.put(streetLightElement.toJSON());
        }

        return listStreetLight;
    }
}