package com.example.stephanielin.represent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by stephanielin on 3/10/16.
 */
public class ParseJSON {

    private String info;
    private String county;
    private String state;
    private Context context;

    public ParseJSON(String county, String state, Context context) {
        this.info = county + ", " + state + ";";
        this.county = county;
        this.state = state;
        this.context = context;
    }

    public String find2012() {
        InputStream stream = null;
        String jsonString= "";
        try {
            stream = context.getAssets().open("election-county-2012.json");
            int size = stream.available();

            byte[] buffer = new byte[size];

            stream.read(buffer);

            stream.close();

            jsonString = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray data = new JSONArray(jsonString);
            for (int i = 0; i < data.length(); i++) {
                JSONObject actor = data.getJSONObject(i);
                String obama;
                String romney;
                if (actor.getString("county-name").equals(county.substring(0, county.lastIndexOf(" "))) &&
                        actor.getString("state-postal").equals(state)) {
                    obama = "Obama: " + actor.getString("obama-percentage") + "% of vote;";
                    romney = "Romney: " + actor.getString("romney-percentage") + "% of vote;";
                    info += obama + romney;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info;
    }

}
