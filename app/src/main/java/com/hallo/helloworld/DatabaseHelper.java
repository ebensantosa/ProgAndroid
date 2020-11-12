package com.hallo.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_login";
    public static final String table_name = "table_login";

    public static final String row_id = "_id";
    public static final String row_username = "Username";
    public static final String row_password = "Password";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_username + " TEXT," + row_password + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }


    public boolean checkUser(String username, String password){
        String[] columns = {row_id};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username,password};
        Cursor cursor = db.query(table_name, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count>0)
            return true;
        else
            return false;
    }
}
