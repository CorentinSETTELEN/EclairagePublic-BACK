package com.example.apirest;

import org.json.JSONException;
import org.json.JSONObject;

public class StreetLight {
    private long _id;
    private String _idrecord;
    private String _content;

    public long get_id() {
        return _id;
    }

    public String get_idrecord() {
        return _idrecord;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public StreetLight(long id, String idrecord, String content)
    {
        this._id = id;
        this._idrecord = idrecord;
        this._content = content;
    }



    public JSONObject toJSON() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("id", _id);
        jo.put("idrecord", _idrecord);
        jo.put("content", _content);

        return jo;
    }
}
