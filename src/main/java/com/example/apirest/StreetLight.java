package com.example.apirest;

import org.json.JSONException;
import org.json.JSONObject;

public class StreetLight {
    private long _id;
    private String _idrecord;
    private String _content;
    private String _flux_lampe;
    private Double _latitude;
    private Double _longitude;
    private String _arrondissement;
    private String _rue;
    private String _voie;

    public StreetLight(long id, String idrecord, String content, String flux_lampe, Double latitude, Double longitude,
                       String arrondissement, String rue, String voie)
    {
        this._id = id;
        this._idrecord = idrecord;
        this._content = content;
        this._flux_lampe = flux_lampe;
        this._latitude = latitude;
        this._longitude = longitude;
        this._arrondissement = arrondissement;
        this._rue = rue;
        this._voie = voie;
    }

    public long get_id() {
        return _id;
    }

    public String get_idrecord() {
        return _idrecord;
    }

    public String get_flux_lampe() { return _flux_lampe; }

    public void set_flux_lampe(String _flux_lampe) { this._flux_lampe = _flux_lampe; }

    public Double get_latitude() { return _latitude; }

    public void set_latitude(Double _latitude) { this._latitude = _latitude; }

    public Double get_longitude() { return _longitude; }

    public void set_longitude(Double _longitude) { this._longitude = _longitude; }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public String get_arrondissement() { return _arrondissement; }

    public void set_arrondissement(String _arrondissement) { this._arrondissement = _arrondissement; }

    public String get_rue() { return _rue; }

    public void set_rue(String _rue) { this._rue = _rue; }

    public String get_voie() { return _voie; }

    public void set_voie(String _voie) { this._voie = _voie; }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("id", _id);
        jo.put("idrecord", _idrecord);
        jo.put("content", _content);
        jo.put("flux_lamp", _flux_lampe);
        jo.put("latitude", _latitude);
        jo.put("longitude", _longitude);
        jo.put("arrondissement", _arrondissement);
        jo.put("rue", _rue);
        jo.put("voie", _voie);

        return jo;
    }
}
