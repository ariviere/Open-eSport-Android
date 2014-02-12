package com.ar.oe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ar.oe.classes.Post;

import java.util.ArrayList;

/**
 * Created by ariviere on 10/22/13.
 */
public class CustomPosts {
    public ArrayList<Post> getCustomPosts(Context context, ArrayList<Post> posts){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        if(!sharedPref.getBoolean("millenium", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Millenium"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("esfr", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("eSportsFrance"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("teamaaa", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Team aAa"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("ogaming", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("O Gaming"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("esportactu", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Esport Actu"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("thunderbot", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Thunderbot"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("vakarm", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("VaKarM"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("iewt", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("IEWT"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("teamliquid", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("TeamLiquid"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("reddit", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Reddit"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("ongamers", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("onGamers"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("skgaming", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("SK Gaming"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("hltv", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("HLTV"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("joindota", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("joinDOTA"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("dota2fr", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("dota2fr"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("esportsexpress", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Esports Express"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("shoryuken", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Shoryuken"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("esreality", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("ESReality"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("esportsheaven", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getWebsite().equals("Esports Heaven"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("lol", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getThumbnail().equals("lol"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("sc2", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getThumbnail().equals("sc2"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("dota2", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getThumbnail().equals("dota2"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("csgo", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getThumbnail().equals("csgo"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("ql", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getThumbnail().equals("ql"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("versus", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getThumbnail().equals("versus"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("hearthstone", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(posts.get(i).getThumbnail().equals("hearthstone"))
                    posts.remove(i);
            }
        }
        if(!sharedPref.getBoolean("others", false)){
            for(int i = posts.size()-1 ; i >= 0 ; i--){
                if(!posts.get(i).getThumbnail().equals("lol") && !posts.get(i).getThumbnail().equals("sc2") && !posts.get(i).getThumbnail().equals("dota2")
                        && !posts.get(i).getThumbnail().equals("csgo") && !posts.get(i).getThumbnail().equals("ql") && !posts.get(i).getThumbnail().equals("versus")
                        && !posts.get(i).getThumbnail().equals("hearthstone"))
                    posts.remove(i);
            }
        }
        return posts;
    }
}
