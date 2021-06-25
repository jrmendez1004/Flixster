package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Images {
    JSONArray poster;
    String Image_size;
    public Images(JSONObject jsonObject) throws JSONException {
        poster = jsonObject.getJSONArray("poster_sizes");
        //Image_size = poster.getString(3);
        //int temp = poster.length();
    }

    public String getImage_size() throws JSONException {
        //return Image_size;
        return poster.getString(3);
    }
}