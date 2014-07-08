package com.ar.oe.utils;

import android.content.Context;
import android.util.Log;

import com.ar.oe.R;
import com.ar.oe.classes.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ariviere on 18/02/2014.
 */
public class JSONFilesManager {
    private static String getOutputStream(Context context, int resourceId){
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;

        try{
            ctr = inputStream.read();
            while (ctr != -1){
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return byteArrayOutputStream.toString();
    }

    public static ArrayList<Category> getCategoriesList(Context context, String type){
        String contentString = null;

        if(type.equals("website"))
            contentString = getOutputStream(context, R.raw.websites);
        else if(type.equals("language"))
            contentString = getOutputStream(context, R.raw.languages);
        else if(type.equals("game"))
            contentString = getOutputStream(context, R.raw.games);

        ArrayList<Category> items = new ArrayList<Category>();

        try {
            JSONArray jsonArray = new JSONArray(contentString);

            for(int i = 0 ; i < jsonArray.length() ; i++){
                Category item = new Category();

                JSONObject jsonItem = jsonArray.optJSONObject(i);

                if(jsonItem != null) {
                    item.setType(type);
                    item.setName(jsonItem.optString("name"));
                    item.setIcon(jsonItem.optString("id"));
                    item.setEnabled(jsonItem.optBoolean("enabled"));

                    if (type.equals("websites")) {
                        item.setLanguage(jsonItem.optString("lang"));
                    }

                    items.add(item);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static HashMap<String, String> getCategoriesMap(Context context, String type){
        String contentString = null;

        if(type.equals("website"))
            contentString = getOutputStream(context, R.raw.websites);
        else if(type.equals("game"))
            contentString = getOutputStream(context, R.raw.games);

        HashMap<String, String> items = new HashMap<String, String>();

        try {
            JSONArray jsonArray = new JSONArray(contentString);

            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonItem = jsonArray.optJSONObject(i);
                items.put(jsonItem.optString("name"), jsonItem.optString("id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}
