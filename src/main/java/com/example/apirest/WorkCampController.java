package com.example.apirest;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class WorkCampController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value="/chantiers-perturbants/{param}", produces = "application/json")
    public <GET, Path, Produces> String getChantiersPertubantsTown(@PathVariable String param) throws JSONException
    {
        // return new StreetLight(counter.incrementAndGet(), "", String.format(template, name));
        return WorkCampController.callOpenDataParisApi(param, "").toString();
    }

    @GetMapping("/chantiers-perturbants")
    public String getChantiersPertubants(@RequestParam(value = "district", defaultValue = "") String param) throws JSONException {
        return WorkCampController.callOpenDataParisApi(param, "").toString();
    }

    @GetMapping("/chantiers-perturbants/{param}/{recordid}")
    public String getChantiersPertubantsSingle(@PathVariable String param, @PathVariable String recordid) throws JSONException {
        return WorkCampController.callOpenDataParisApi(param, recordid).toString();
    }

    /*
    Call opendata.paris API with param
     */
    static JSONArray callOpenDataParisApi(String param, String recordid)
    {
        System.out.println(param);

        JSONArray output = new JSONArray();

        try {
            // URL url = new URL("https://opendata.paris.fr/api/records/1.0/search/?dataset=eclairage-public&facet=ville&refine.ville=" + dataset);
            URL url = new URL("https://opendata.paris.fr/api/records/1.0/search/?dataset=chantiers-perturbants" + param);
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

            if(recordid == ""){
                while ((line = br.readLine()) != null) {
                    output = WorkCampController.jsonToObject(line);
                }
            }else{
                while ((line = br.readLine()) != null) {
                    output = WorkCampController.jsonToObjectGetSingle(line, recordid);
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }

    /*
    List all WorkCamps
     */
    public static JSONArray jsonToObject(String jsonString) throws JSONException {
        JSONObject jsonDecode = new JSONObject(jsonString);
        JSONArray jsonDecodeRecords = jsonDecode.getJSONArray("records");

        int length = jsonDecodeRecords.length();

        JSONArray listWorkCamp = new JSONArray();
        for (int i=0;i<length;i++){
            JSONObject element = jsonDecodeRecords.getJSONObject(i);
            listWorkCamp.put(WorkCampController.getJsonContentApi(element, i));
        }

        return listWorkCamp;
    }

    /*
     List elements and get the WorkCamps from recordId
     */
    public static JSONArray jsonToObjectGetSingle(String jsonString, String recordId) throws JSONException {
        JSONObject jsonDecode = new JSONObject(jsonString);
        JSONArray jsonDecodeRecords = jsonDecode.getJSONArray("records");

        int length = jsonDecodeRecords.length();

        JSONArray listWorkCamp = new JSONArray();
        for (int i=0;i<length;i++){
            JSONObject element = jsonDecodeRecords.getJSONObject(i);

            if(element.getString("recordid").trim().equals(recordId)){
                listWorkCamp.put(WorkCampController.getJsonContentApi(element, i));
                break;
            }else{
                continue;
            }
        }

        return listWorkCamp;
    }

    /*
    Exploit the json content from api's request
     */
    public static JSONObject getJsonContentApi(JSONObject element, int i) throws JSONException {

        JSONObject elementGeometry = element.getJSONObject("geometry");
        JSONArray elementCoord = elementGeometry.getJSONArray("coordinates");

        Double elementCoordAltitude = elementCoord.getDouble(0);
        Double elementCoordLongitude = elementCoord.getDouble(1);

        JSONObject elementField = element.getJSONObject("fields");
        System.out.println(elementField.has("numvoie_ou") ? elementField.getString("numvoie_ou") : "");
        StreetLight streetLightElement = new StreetLight(
                i + 1,
                element.getString("recordid").trim(),
                elementField.getString("objet"),
                elementField.getString("maitre_ouvrage"),
                elementCoordAltitude,
                elementCoordLongitude,
                elementField.getString("date_creation"),
                elementField.getString("date_fin"),
                elementField.getString("cp_arrondissement")
        );

        return streetLightElement.toJSON();
    }
}