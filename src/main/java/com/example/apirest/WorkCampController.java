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
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=chantiers-perturbants"  + param;
        return WorkCampController.callOpenDataController(url_string);
    }

    @GetMapping("/chantiers-perturbants")
    public String getChantiersPertubants(@RequestParam(value = "district", defaultValue = "") String param) throws JSONException {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=chantiers-perturbants";
        return WorkCampController.callOpenDataController(url_string);
    }

    @GetMapping("/chantiers-perturbants/id/{recordid}")
    public String getChantiersPertubantsSingle(@PathVariable String recordid) throws JSONException {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=chantiers-perturbants&facet=recordid&refine.recordid=" + recordid;
        return WorkCampController.callOpenDataController(url_string);
    }

    public static String callOpenDataController(String url_string) throws JSONException
    {
        JSONArray outputs = OpenDataApi.callApi(url_string);
        int length = outputs.length();

        JSONArray listStreetLight = new JSONArray();
        for (int i=0;i<length;i++){
            JSONObject element = outputs.getJSONObject(i);

            listStreetLight.put(WorkCampController.getJsonContentApi(element, i));
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
        System.out.println(elementField);
        WorkCamp WorkCampElement = new WorkCamp(
                i + 1,
                element.getString("recordid").trim(),
                elementField.has("objet") ? elementField.getString("objet") : "",
                elementField.has("maitre_ouvrage") ? elementField.getString("maitre_ouvrage") : "",
                elementCoordAltitude,
                elementCoordLongitude,
                elementField.getString("date_creation"),
                elementField.getString("date_fin"),
                elementField.getString("cp_arrondissement")
        );

        return WorkCampElement.toJSON();
    }
}