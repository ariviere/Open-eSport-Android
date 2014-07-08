package com.ar.oe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ar.oe.classes.Category;
import com.ar.oe.classes.Post;

import java.util.ArrayList;

/**
 * Created by ariviere on 10/22/13.
 */
public class CustomPosts {
    public ArrayList<Post> getCustomPosts(Context context, ArrayList<Post> posts){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        ArrayList<Category> websitesList = JSONFilesManager.getCategoriesList(context, "website");
        for(Category website : websitesList){
            if(!sharedPref.getBoolean(website.getIcon(), website.isEnabled())){
                for(int i = posts.size()-1 ; i >= 0 ; i--){
                    if(posts.get(i).getWebsite().equals(website.getName()))
                        posts.remove(i);
                }
            }
        }

        ArrayList<Category> languagesList = JSONFilesManager.getCategoriesList(context, "language");
        for(Category language : languagesList){
            if(!sharedPref.getBoolean(language.getIcon(), language.isEnabled())){
                for(int i = posts.size()-1 ; i >= 0 ; i--){
                    if(posts.get(i).getLanguage().equals(language.getIcon()))
                        posts.remove(i);
                }
            }
        }

        ArrayList<Category> gamesList = JSONFilesManager.getCategoriesList(context, "game");
        ArrayList<String> gamesTitles = new ArrayList<String>();
        for(Category game : gamesList) {
            gamesTitles.add(game.getIcon());
        }

        for(Category game : gamesList) {
            if (!sharedPref.getBoolean(game.getIcon(), game.isEnabled())) {
                for (int i = posts.size() - 1; i >= 0; i--) {
                    if (posts.get(i).getThumbnail().equals(game.getIcon()) || (game.getIcon().equals("other") && !gamesTitles.contains(posts.get(i).getThumbnail())))
                        posts.remove(i);
                }
            }
        }

        return posts;
    }
}
