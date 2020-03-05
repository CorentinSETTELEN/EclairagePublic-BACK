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
public class StreetLightController {

    // @GetMapping("/eclairage-public/{town}", produces = { "application/json" })
    @GetMapping(value="/eclairage-public/{param}", produces = "application/json")
    public <GET, Path, Produces> String getEclairagePublicTown(@PathVariable String param) throws JSONException
    {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=eclairage-public" + param;
        return StreetLightController.callOpenDataController(url_string);
    }

    @GetMapping("/eclairage-public")
    public String getEclairagePublic(@RequestParam(value = "district", defaultValue = "") String param) throws JSONException
    {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=eclairage-public" + param;
        return StreetLightController.callOpenDataController(url_string);
    }

    @GetMapping(value="/eclairage-public/id/{recordid}", produces = "application/json")
    public String getEclairagePublicSingle(@PathVariable String recordid) throws JSONException
    {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=eclairage-public&facet=recordid&refine.recordid=" + recordid;
        return StreetLightController.callOpenDataController(url_string);
    }

    public static String callOpenDataController(String url_string) throws JSONException
    {
        JSONArray outputs = OpenDataApi.callApi(url_string);
        int length = outputs.length();

        JSONArray listStreetLight = new JSONArray();
        for (int i=0;i<length;i++){
            JSONObject element = outputs.getJSONObject(i);

            listStreetLight.put(StreetLightController.getJsonContentApi(element, i));
        }

        return listStreetLight.toString();
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
                element.getString("datasetid"),
                elementField.getString("flux_lampe"),
                elementCoordAltitude,
                elementCoordLongitude,
                elementField.getString("lib_region"),
                elementField.has("numvoie_ou") ? elementField.getString("numvoie_ou") : "",
                elementField.getString("lib_voie")
        );

        return streetLightElement.toJSON();
    }
}