package com.ar.oe.utils;

import android.provider.BaseColumns;

public class DatabaseSettings implements BaseColumns{

    public static final String postsTable="posts";
    static final String colTitle="Title";
    static final String colWebsite="Website";
    static final String colCategory="Category";
    static final String colLink="Link";
    static final String colDate="Date";
    static final String colAuthor="Author";
    static final String colLanguage="Language";
    static final String colTitleDate="TitleDate";


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    protected DatabaseSettings(){
    }

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseSettings.postsTable + " (" +
                    DatabaseSettings._ID + " INTEGER PRIMARY KEY," +
                    DatabaseSettings.colTitle + TEXT_TYPE + COMMA_SEP +
                    DatabaseSettings.colWebsite + TEXT_TYPE + COMMA_SEP +
                    DatabaseSettings.colCategory + TEXT_TYPE + COMMA_SEP +
                    DatabaseSettings.colLink + TEXT_TYPE + COMMA_SEP +
                    DatabaseSettings.colDate + TEXT_TYPE + COMMA_SEP +
                    DatabaseSettings.colAuthor + TEXT_TYPE + COMMA_SEP +
                    DatabaseSettings.colLanguage + TEXT_TYPE + COMMA_SEP +
                    DatabaseSettings.colTitleDate + TEXT_TYPE +
            " )";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseSettings.postsTable;

}

