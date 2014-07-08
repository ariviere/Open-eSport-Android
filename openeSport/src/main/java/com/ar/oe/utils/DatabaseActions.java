package com.ar.oe.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ar.oe.classes.Category;
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
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
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

    public ArrayList<Post> readDb(Context context, String category){

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
        if(category.equals("oe_menu")) {
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

            ArrayList<Category> gamesList = JSONFilesManager.getCategoriesList(context, "game");

            for(Category game : gamesList){
                if(game.getIcon().equals(category)){
                    whereColumn = DatabaseSettings.colCategory;
                    whereValue = category;
                }
            }
            ArrayList<Category> websitesList = JSONFilesManager.getCategoriesList(context, "website");
            for(Category website : websitesList){
                if(website.getIcon().equals(category)){
                    whereColumn = DatabaseSettings.colWebsite;
                    whereValue = website.getName();
                }
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