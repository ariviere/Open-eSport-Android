package com.ar.oe.utils;

import android.content.Context;

import com.ar.oe.R;
import com.ar.oe.classes.DrawerItem;
import com.ar.oe.classes.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppDatas {
    public ArrayList<DrawerItem> getMenuItems(Context context){
        ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.all), "oe_menu_drawer", "all"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.custom), "custom", "custom"));

        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.language), null, "header"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.french), "fr_drawer", "language"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.english), "en_drawer", "language"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.games), null, "header"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.lol), "lol_drawer", "game"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.sc2), "sc2_drawer", "game"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.dota2), "dota2_drawer", "game"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.csgo), "csgo_drawer", "game"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.versus), "versus_drawer", "game"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.ql), "ql_drawer", "game"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.hearthstone), "hearthstone_drawer", "game"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.websites), null, "header"));
//        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.esfr), "esportsfrance_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.ogaming), "ogaming_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.teamaaa), "teamaaa_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.millenium), "millenium_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.esportactu), "esportactu_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.thunderbot), "thunderbot_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.iewt), "iewt_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.vakarm), "vakarm_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.dota2fr), "dota2fr_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.reddit), "reddit_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.ongamers), "ongamers_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.esportsheaven), "esportsheaven_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.skgaming), "skgaming_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.hltv), "hltv_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.teamliquid), "teamliquid_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.joindota), "joindota_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.shoryuken), "shoryuken_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.esreality), "esreality_drawer", "website"));
        drawerItems.add(new DrawerItem(context.getResources().getString(R.string.esportsexpress), "esportsexpress_drawer", "website"));

        return drawerItems;
    }

    public ArrayList<DrawerItem> getWebsites(Context context){
        ArrayList<DrawerItem> websites = new ArrayList<DrawerItem>();
        websites.add(new DrawerItem(context.getResources().getString(R.string.ogaming), "ogaming_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.teamaaa), "teamaaa_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.millenium), "millenium_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.esportactu), "esportactu_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.thunderbot), "thunderbot_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.iewt), "iewt_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.vakarm), "vakarm_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.dota2fr), "dota2fr_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.hltv), "hltv_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.reddit), "reddit_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.ongamers), "ongamers_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.esportsheaven), "esportsheaven_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.skgaming), "skgaming_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.teamliquid), "teamliquid_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.joindota), "joindota_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.shoryuken), "shoryuken_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.esreality), "esreality_drawer"));
        websites.add(new DrawerItem(context.getResources().getString(R.string.esportsexpress), "esportsexpress_drawer"));

        return websites;
    }

    public Map<String, String> getWebsitesIcons(Context context){
        Map<String, String> websites = new HashMap<String, String>();
        websites.put(context.getResources().getString(R.string.ogaming_brut), "ogaming");
        websites.put(context.getResources().getString(R.string.teamaaa), "teamaaa");
        websites.put(context.getResources().getString(R.string.millenium), "millenium");
        websites.put(context.getResources().getString(R.string.esportactu), "esportactu");
        websites.put(context.getResources().getString(R.string.thunderbot), "thunderbot");
        websites.put(context.getResources().getString(R.string.iewt), "iewt");
        websites.put(context.getResources().getString(R.string.vakarm), "vakarm");
        websites.put(context.getResources().getString(R.string.dota2fr), "dota2fr");
        websites.put(context.getResources().getString(R.string.hltv), "hltv");
        websites.put(context.getResources().getString(R.string.reddit), "reddit");
        websites.put(context.getResources().getString(R.string.ongamers), "ongamers");
        websites.put(context.getResources().getString(R.string.esportsheaven), "esportsheaven");
        websites.put(context.getResources().getString(R.string.skgaming), "skgaming");
        websites.put(context.getResources().getString(R.string.teamliquid), "teamliquid");
        websites.put(context.getResources().getString(R.string.joindota), "joindota");
        websites.put(context.getResources().getString(R.string.shoryuken), "shoryuken");
        websites.put(context.getResources().getString(R.string.esreality), "esreality");
        websites.put(context.getResources().getString(R.string.esportsexpress), "esportsexpress");

        return websites;
    }


    public String[] getGamesName(){
        String[] gamesName = { "TOUT", "LOL", "SC2", "DOTA2", "CS:GO", "QL", "VS", "HS", "#" };
        return gamesName;
    }

    public int[] getGamesDrawable(){
        int[] gamesDrawable = {
                R.drawable.oe_filter_logo,
                R.drawable.lol,
                R.drawable.sc2,
                R.drawable.dota2,
                R.drawable.csgo,
                R.drawable.ql,
                R.drawable.versus,
                R.drawable.hearthstone,
                R.drawable.other};
        return gamesDrawable;
    }

    public String[] getLangsName(){
        String[] langsName = { "FR", "EN" };
        return langsName;
    }

    public int[] getLangsDrawable(){
        int[] langsDrawable = {
                R.drawable.fr_drawer,
                R.drawable.en_drawer};
        return langsDrawable;
    }

    public ArrayList<Post> sortPosts(String game, ArrayList<Post> posts){
        ArrayList<Post> sortedPosts = new ArrayList<Post>();
        if(game.equals("TOUT")){
            return posts;
        }
        else if(game.equals("#")){
            for(Post post : posts){
                if(!post.getThumbnail().equals("sc2") && !post.getThumbnail().equals("lol") && !post.getThumbnail().equals("dota2") && !post.getThumbnail().equals("csgo")
                        && !post.getThumbnail().equals("ql") && !post.getThumbnail().equals("versus") && !post.getThumbnail().equals("hearthstone")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (game.equals("SC2")){
            for(Post post : posts){
                if(post.getThumbnail().equals("sc2")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (game.equals("LOL")){
            for(Post post : posts){
                if(post.getThumbnail().equals("lol")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (game.equals("DOTA2")){
            for(Post post : posts){
                if(post.getThumbnail().equals("dota2")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (game.equals("CS:GO")){
            for(Post post : posts){
                if(post.getThumbnail().equals("csgo")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (game.equals("QL")){
            for(Post post : posts){
                if(post.getThumbnail().equals("ql")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (game.equals("VS")){
            for(Post post : posts){
                if(post.getThumbnail().equals("versus")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (game.equals("HS")){
            for(Post post : posts){
                if(post.getThumbnail().equals("hearthstone")){
                    sortedPosts.add(post);
                }
            }
        }
        return sortedPosts;
    }

    public ArrayList<Post> sortPostsv2(String lang, ArrayList<Post> posts){
        ArrayList<Post> sortedPosts = new ArrayList<Post>();
        if (lang.equals("FR")){
            for(Post post : posts){
                if(post.getLanguage().equals("fr")){
                    sortedPosts.add(post);
                }
            }
        }
        else if (lang.equals("EN")){
            for(Post post : posts){
                if(post.getLanguage().equals("en")){
                    sortedPosts.add(post);
                }
            }
        }
        return sortedPosts;
    }
}