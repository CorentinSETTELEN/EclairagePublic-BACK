package com.example.apirest;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkCamp {
    private long _id;
    private String _idrecord;
    private String _objet;
    private String _maitre_ouvrage;
    private Double _latitude;
    private Double _longitude;
    private String _date_start;
    private String _date_end;
    private String _arrondissement;

    public WorkCamp(long id, String idrecord, String objet, String maitre_ouvrage, Double latitude, Double longitude,
                       String date_start, String date_end, String arrondissement)
    {
        this._id = id;
        this._idrecord = idrecord;
        this._objet = objet;
        this._maitre_ouvrage = maitre_ouvrage;
        this._latitude = latitude;
        this._longitude = longitude;
        this._date_start = date_start;
        this._date_end = date_end;
        this._arrondissement = arrondissement;
    }

    public long get_id() {
        return _id;
    }

    public String get_idrecord() {
        return _idrecord;
    }

    public String get_objet() { return _objet; }

    public void set_objet(String _objet) { this._objet = _objet; }

    public String get_maitre_ouvrage() { return _maitre_ouvrage; }

    public void set_maitre_ouvrage(String _maitre_ouvrage) { this._maitre_ouvrage = _maitre_ouvrage; }

    public Double get_latitude() { return _latitude; }

    public void set_latitude(Double _latitude) { this._latitude = _latitude; }

    public Double get_longitude() { return _longitude; }

    public void set_longitude(Double _longitude) { this._longitude = _longitude; }

    public String get_date_start() { return _date_start; }

    public void set_date_start(String _date_start) { this._date_start = _date_start; }

    public String get_date_end() { return _date_end; }

    public void set_date_end(String _date_end) { this._date_end = _date_end; }

    public String get_arrondissement() { return _arrondissement; }

    public void set_arrondissement(String _arrondissement) { this._arrondissement = _arrondissement; }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("id", _id);
        jo.put("idrecord", _idrecord);
        jo.put("objet", _objet);
        jo.put("maitre_ouvrage", _maitre_ouvrage);
        jo.put("latitude", _latitude);
        jo.put("longitude", _longitude);
        jo.put("date_start", _date_start);
        jo.put("date_end", _date_end);
        jo.put("arrondissement", _arrondissement);

        return jo;
    }
}
