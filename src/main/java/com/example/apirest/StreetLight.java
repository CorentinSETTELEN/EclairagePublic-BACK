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

    public StreetLight(long id, String idrecord, String content, String flux_lampe, Double latitude, Double longitude)
    {
        this._id = id;
        this._idrecord = idrecord;
        this._content = content;
        this._flux_lampe = flux_lampe;
        this._latitude = latitude;
        this._longitude = longitude;
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

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("id", _id);
        jo.put("idrecord", _idrecord);
        jo.put("content", _content);
        jo.put("flux_lamp", _flux_lampe);
        jo.put("latitude", _latitude);
        jo.put("longitude", _longitude);

        return jo;
    }
}
