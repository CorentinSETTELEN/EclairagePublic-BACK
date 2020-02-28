package com.example.apirest;

import org.json.JSONException;
import org.json.JSONObject;

public class StreetLight {
    private final long _id;
    private final String _idrecord;
    private final String _content;

    public StreetLight(long id, String idrecord, String content)
    {
        this._id = id;
        this._idrecord = idrecord;
        this._content = content;
    }

    public long getId() {
        return _id;
    }

    public String getIdrecord() {
        return _idrecord;
    }

    public String getContent() {
        return _content;
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("id", _id);
        jo.put("idrecord", _idrecord);
        jo.put("content", _content);

        return jo;
    }
}
