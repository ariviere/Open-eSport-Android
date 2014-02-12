package com.ar.oe.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ar.oe.classes.Post;

import java.util.ArrayList;

/**
 * Created by aereivir on 18/09/13.
 */

public class DatabaseActions extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "openesport.db";
    private static DatabaseSettings dc = new DatabaseSettings();


    public DatabaseActions(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseSettings.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(DatabaseSettings.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertColumns(ArrayList<Post> posts){
        SQLiteDatabase db = this.getWritableDatabase();

        for(Post post : posts){
            ContentValues cv = new ContentValues();
            cv.put(DatabaseSettings.colTitle, post.getTitle());
            cv.put(DatabaseSettings.colWebsite, post.getWebsite());
            cv.put(DatabaseSettings.colCategory, post.getThumbnail());
            cv.put(DatabaseSettings.colLink, post.getUrl());
            cv.put(DatabaseSettings.colDate, post.getPubDate());
            cv.put(DatabaseSettings.colAuthor, post.getAuthor());
            cv.put(DatabaseSettings.colLanguage, post.getLanguage());
            db.insert(DatabaseSettings.postsTable, null, cv);
        }

        db.close();
    }

    public ArrayList<Post> readDb(String category){

        ArrayList<Post> posts = new ArrayList<Post>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseSettings._ID,
                DatabaseSettings.colTitle,
                DatabaseSettings.colWebsite,
                DatabaseSettings.colCategory,
                DatabaseSettings.colLink,
                DatabaseSettings.colDate,
                DatabaseSettings.colAuthor,
                DatabaseSettings.colLanguage
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseSettings.colDate + " DESC";

        Cursor cursor = null;
        if(category.equals("oe_menu_drawer")) {
            cursor = db.query(
                    DatabaseSettings.postsTable,  // The table to query
                    projection,                               // The columns to return
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
        }else{
            //remove drawer attribute from category
            category = category.split("_")[0];

            String whereColumn = null;
            String whereValue = null;

            if(category.equals("fr")){
                whereColumn = DatabaseSettings.colLanguage;
                whereValue = category;
            }
            else if(category.equals("en")){
                whereColumn = DatabaseSettings.colLanguage;
                whereValue = category;
            }
            else if(category.equals("lol")){
                whereColumn = DatabaseSettings.colCategory;
                whereValue = "lol";
            }
            else if(category.equals("sc2")){
                whereColumn = DatabaseSettings.colCategory;
                whereValue = "sc2";
            }
            else if(category.equals("dota2")){
                whereColumn = DatabaseSettings.colCategory;
                whereValue = "dota2";
            }
            else if(category.equals("csgo")){
                whereColumn = DatabaseSettings.colCategory;
                whereValue = "csgo";
            }
            else if(category.equals("versus")){
                whereColumn = DatabaseSettings.colCategory;
                whereValue = "versus";
            }
            else if(category.equals("ql")){
                whereColumn = DatabaseSettings.colCategory;
                whereValue = "ql";
            }
            else if(category.equals("hearthstone")){
                whereColumn = DatabaseSettings.colCategory;
                whereValue = "hearthstone";
            }
            else if(category.equals("esportsfrance")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "eSportsFrance";
            }
            else if(category.equals("esportsfrance")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "eSportsFrance";
            }
            else if(category.equals("teamaaa")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Team aAa";
            }
            else if(category.equals("millenium")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Millenium";
            }
            else if(category.equals("esportactu")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Esport Actu";
            }
            else if(category.equals("vakarm")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "VaKarM";
            }
            else if(category.equals("thunderbot")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Thunderbot";
            }
            else if(category.equals("dota2fr")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "dota2fr";
            }
            else if(category.equals("iewt")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "IEWT";
            }
            else if(category.equals("ogaming")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "O Gaming";
            }
            else if(category.equals("reddit")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Reddit";
            }
            else if(category.equals("ongamers")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "onGamers";
            }
            else if(category.equals("esportsheaven")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Esports Heaven";
            }
            else if(category.equals("skgaming")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "SK Gaming";
            }
            else if(category.equals("hltv")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "HLTV";
            }
            else if(category.equals("teamliquid")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "TeamLiquid";
            }
            else if(category.equals("joindota")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "joinDOTA";
            }
            else if(category.equals("esportsexpress")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Esports Express";
            }
            else if(category.equals("shoryuken")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "Shoryuken";
            }
            else if(category.equals("esreality")){
                whereColumn = DatabaseSettings.colWebsite;
                whereValue = "ESReality";
            }
            cursor = db.query(
                    DatabaseSettings.postsTable,  // The table to query
                    projection,                               // The columns to return
                    whereColumn + " = '" + whereValue.replace("'", "\\'") + "'",                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
        }



        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Post post = new Post();
            post.setTitle(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseSettings.colTitle)));
            post.setWebsite(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseSettings.colWebsite)));
            post.setThumbnail(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseSettings.colCategory)));
            post.setUrl(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseSettings.colLink)));
            post.setPubDate(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseSettings.colDate)));
            post.setAuthor(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseSettings.colAuthor)));
            post.setLanguage(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseSettings.colLanguage)));
            posts.add(post);
            cursor.moveToNext();
        }

        return posts;
    }

    public void removeDatas(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseSettings.postsTable, null, null);
    }
}