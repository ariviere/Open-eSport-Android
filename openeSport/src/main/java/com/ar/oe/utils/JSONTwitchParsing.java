package com.ar.oe.utils;

import com.ar.oe.classes.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONTwitchParsing {
    public static ArrayList<Stream> getJSONfromGame(String game){
        String url = getUrl(game);
        //initialize
        InputStream is = null;
        String result = "";
        ArrayList<Stream> streams = new ArrayList<Stream>();

        try{

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("Accept", "application/json");
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        }catch(Exception e){
        }

        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
        }catch(Exception e){
        }


        //try parse the string to a JSON object
        try{
            JSONParser parser  =new JSONParser();
            Object obj = parser.parse(result);
            JSONObject twitchJson = (JSONObject)obj;
            JSONArray streamsArray=(JSONArray)twitchJson.get("streams");

            for(int i = 0 ; i < streamsArray.size() ; i++){
                JSONObject streamObject=(JSONObject)streamsArray.get(i);
                JSONObject channelObject=(JSONObject)streamObject.get("channel");
                Stream stream = new Stream();
                if(channelObject.get("logo") != null)
                    stream.setImg(channelObject.get("logo").toString());
                if(channelObject.get("display_name") != null)
                    stream.setName(channelObject.get("display_name").toString());
                if(channelObject.get("status") != null)
                    stream.setStatus(channelObject.get("status").toString());
                if(channelObject.get("url") != null)
                    stream.setUrl(channelObject.get("url").toString());
                if(channelObject.get("viewers") != null)
                    stream.setViewers(streamObject.get("viewers").toString());

                if(streamObject.get("game") != null)
                    stream.setGame(getGame(streamObject.get("game").toString()));

                streams.add(stream);
            }
            return streams;

        }catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getGame(String fullGame){
        if(fullGame.toLowerCase().contains("league of legends"))
            return "lol";
        else if(fullGame.toLowerCase().contains("starcraft"))
            return "sc2";
        else if(fullGame.toLowerCase().contains("dota"))
            return "dota2";
        else if(fullGame.toLowerCase().contains("counter strike"))
            return "csgo";
        else if(fullGame.toLowerCase().contains("quake"))
            return "ql";
        else if(fullGame.toLowerCase().contains("street fighter"))
            return "versus";
        else if(fullGame.toLowerCase().contains("hearthstone"))
            return "hearthstone";
        else
            return "other";
    }

    private static String getUrl(String game){
        String gameUrl = null;
        if(game.equals("lol"))
            gameUrl = "https://api.twitch.tv/kraken/streams?game=League+of+Legends";
        else if(game.equals("sc2"))
            gameUrl = "https://api.twitch.tv/kraken/streams?game=StarCraft+II:+Heart+of+the+Swarm";
        else if(game.equals("dota2"))
            gameUrl = "https://api.twitch.tv/kraken/streams?game=Dota+2";
        else if(game.equals("csgo"))
            gameUrl = "https://api.twitch.tv/kraken/streams?game=Counter-Strike:+Global+Offensive";
        else if(game.equals("versus"))
            gameUrl = "https://api.twitch.tv/kraken/streams?game=Super+Street+Fighter+IV";
        else if(game.equals("ql"))
            gameUrl = "https://api.twitch.tv/kraken/streams?game=Quake+Live";
        else if(game.equals("hearthstone"))
            gameUrl = "https://api.twitch.tv/kraken/streams?game=Hearthstone:+Heroes+of+Warcraft";
        else
            gameUrl = "https://api.twitch.tv/kraken/streams";

        return gameUrl;
    }

}
