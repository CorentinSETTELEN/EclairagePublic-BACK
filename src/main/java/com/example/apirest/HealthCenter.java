package com.example.apirest;

import org.json.JSONException;
import org.json.JSONObject;

public class HealthCenter {
    private long _id;
    private String _idrecord;
    private String _content;
    private String _center_name;
    private Double _latitude;
    private Double _longitude;
    private String _address;
    private String _rue;
    private String _activityName;
    private String _startTime;
    private String _endTime;
    private String _phoneNumber;
    private String _speciality;

    public HealthCenter(long id, String idrecord, String content, String center_name, Double latitude, Double longitude,
                       String address, String activityName, String startTime, String endTime, String phoneNumber, String speciality)
    {
        this._id = id;
        this._idrecord = idrecord;
        this._content = content;
        this._center_name = center_name;
        this._latitude = latitude;
        this._longitude = longitude;
        this._address = address;
        this._activityName = activityName;
        this._startTime = startTime;
        this._endTime = endTime;
        this._phoneNumber = phoneNumber;
        this._speciality = speciality;
    }

    public long get_id() { return _id; }

    public String get_idrecord() { return _idrecord; }

    public String get_content() { return _content; }

    public void set_content(String _content) { this._content = _content; }

    public String get_center_name() { return _center_name; }

    public void set_center_name(String _center_name) { this._center_name = _center_name; }

    public Double get_latitude() { return _latitude; }

    public void set_latitude(Double _latitude) { this._latitude = _latitude; }

    public Double get_longitude() { return _longitude; }

    public void set_longitude(Double _longitude) { this._longitude = _longitude; }

    public String get_address() { return _address; }

    public void set_address(String _address) { this._address = _address; }

    public String get_rue() { return _rue; }

    public void set_rue(String _rue) { this._rue = _rue; }

    public String get_activityName() { return _activityName; }

    public void set_activityName(String _activityName) { this._activityName = _activityName; }

    public String get_startTime() { return _startTime; }

    public void set_startTime(String _startTime) { this._startTime = _startTime; }

    public String get_endTime() { return _endTime; }

    public void set_endTime(String _endTime) { this._endTime = _endTime; }

    public String get_phoneNumber() { return _phoneNumber; }

    public void set_phoneNumber(String _phoneNumber) { this._phoneNumber = _phoneNumber; }

    public String get_speciality() { return _speciality; }

    public void set_speciality(String _speciality) { this._speciality = _speciality; }


    public JSONObject toJSON() throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("id", _id);
        jo.put("idrecord", _idrecord);
        jo.put("content", _content);
        jo.put("center_name", _center_name);
        jo.put("latitude", _latitude);
        jo.put("longitude", _longitude);
        jo.put("address", _address);
        jo.put("activityName", _activityName);
        jo.put("startTime", _startTime);
        jo.put("endTime", _endTime);
        jo.put("phoneNumber", _phoneNumber);
        jo.put("speciality", _speciality);

        return jo;
    }
}
