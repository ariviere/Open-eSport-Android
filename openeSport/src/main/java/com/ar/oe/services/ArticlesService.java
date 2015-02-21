package com.ar.oe.services;

import android.content.Context;
import android.os.Handler;

import com.ar.oe.R;
import com.ar.oe.classes.Post;
import com.ar.oe.utils.JSONParsing;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by ariviere on 11/10/14.
 */
public class ArticlesService {

    public static void getArticles(Context context, ArrayList<String> websites, ArrayList<String> games, int page, Handler handler){
        Thread t = new Thread() {
            public void run() {

                JSONArray jsonRss = JSONParsing.getJSONfromURL(getString(R.string.server_url));

                if(jsonRss != null) {
                    ArrayList<Post> posts = new ArrayList<Post>();
                    for (int i = 0; i < jsonRss.length(); i++) {
                        Post post = new Post();
                        post.setTitle(jsonRss.optJSONObject(i).optString("title"));
                        post.setWebsite(jsonRss.optJSONObject(i).optString("website"));
                        post.setAuthor(jsonRss.optJSONObject(i).optString("author"));
                        post.setThumbnail(jsonRss.optJSONObject(i).optString("category"));
                        post.setLanguage(jsonRss.optJSONObject(i).optString("language"));
                        post.setPubDate(jsonRss.optJSONObject(i).optString("pubDate"));
                        post.setUrl(jsonRss.optJSONObject(i).optString("link"));
                        posts.add(post);
                    }
                    mHandler.post(showDatas);
                }else {
                    mHandler.post(showBlank);
                }
            }
        };
        t.start();
    }
}
