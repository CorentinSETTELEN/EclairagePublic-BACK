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
public class HealthCenterController {

    @GetMapping(value="/centre-sante/{param}", produces = "application/json")
    public <GET, Path, Produces> String getEclairagePublicTown(@PathVariable String param) throws JSONException
    {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=consultations_des_centres_de_sante" + param;
        return HealthCenterController.callOpenDataController(url_string);
    }

    @GetMapping("/centre-sante")
    public String getEclairagePublic(@RequestParam(value = "district", defaultValue = "") String param) throws JSONException
    {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=consultations_des_centres_de_sante" + param;
        return HealthCenterController.callOpenDataController(url_string);
    }

    @GetMapping(value="/centre-sante/id/{recordid}", produces = "application/json")
    public String getEclairagePublicSingle(@PathVariable String recordid) throws JSONException
    {
        String url_string = "https://opendata.paris.fr/api/records/1.0/search/?dataset=consultations_des_centres_de_sante&facet=recordid&refine.recordid=" + recordid;
        return HealthCenterController.callOpenDataController(url_string);
    }

    public static String callOpenDataController(String url_string) throws JSONException
    {
        JSONArray outputs = OpenDataApi.callApi(url_string);
        int length = outputs.length();

        JSONArray listStreetLight = new JSONArray();
        for (int i=0;i<length;i++){
            JSONObject element = outputs.getJSONObject(i);
            listStreetLight.put(HealthCenterController.getJsonContentApi(element, i));
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
        HealthCenter healthCenterElement = new HealthCenter(
                i + 1,
                element.getString("recordid").trim(),
                element.getString("datasetid"),
                elementField.getString("nom_du_centre_de_sante"),
                elementCoordAltitude,
                elementCoordLongitude,
                elementField.getString("adresse_rue"),
                elementField.has("nom_de_lactivite") ? elementField.getString("nom_de_lactivite") : "",
                elementField.getString("heure_de_debut"),
                elementField.getString("heure_de_fin"),
                elementField.getString("numero_de_telephone"),
                elementField.getString("specialite")
        );

        return healthCenterElement.toJSON();
    }
}