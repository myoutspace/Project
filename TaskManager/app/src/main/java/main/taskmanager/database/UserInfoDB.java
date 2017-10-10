package main.taskmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import main.taskmanager.javaActions.*;
import java.util.*;

/**
 * Created by Aury on 05/10/2017.
 * Not done
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class UserInfoDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "registrationInfo.db";
    public static final String GROUP_INFO_TABLE = "groupInfo";
    public static final String USER_INFO_TABLE = "userInfo";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GROUP_NAME = "group";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POINTS = "points";
    private HashMap hp;

    public UserInfoDB(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table " + GROUP_INFO_TABLE + "( " + KEY_ID + " integer primary key, " + KEY_NAME + " text)");
        db.execSQL("create table " + USER_INFO_TABLE + "( " + KEY_ID + " integer primary key, " + KEY_NAME + " text, " +
                KEY_GROUP_NAME + " text, " + KEY_PASSWORD + " text, " + KEY_POINTS + " text, " + KEY_TITLE + "text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + GROUP_INFO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_INFO_TABLE);
        onCreate(db);
    }

    public boolean addGroup(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert(GROUP_INFO_TABLE, null, contentValues);
        return true;
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername()); // User Name
        values.put(KEY_GROUP_NAME, user.getGroupName()); // User Group
        values.put(KEY_PASSWORD, user.getPassword()); // User password
        values.put(KEY_POINTS, user.getPointAmount()); // User points
        values.put(KEY_TITLE, user.getTitle()); // User title
        db.insert(USER_INFO_TABLE, null, values); // Inserting Row
        return true;
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER_INFO_TABLE, new String[]{KEY_NAME, KEY_PASSWORD, KEY_GROUP_NAME, KEY_POINTS, KEY_TITLE}, KEY_NAME+ "=?",
        new String[]{String.valueOf(username)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(cursor.getString(0), cursor.getString(3), cursor.getString(1), cursor.getString(4), cursor.getString(2));
        cursor.close();
        return user; // return User
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<User>(); // Select All Query

        String selectQuery = "SELECT * FROM " + USER_INFO_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do { // Adding user to list
                User user = new User("","","","","");
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setGroupName(cursor.getString(2));
                user.setPointAmount(cursor.getString(3));
                user.setTitle(cursor.getString(4));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList; // return user list
    }

    public Cursor getAllUsers(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + USER_INFO_TABLE + " where id="+KEY_ID+"", null );
        return res;
    }

    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + USER_INFO_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount(); // return count
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_GROUP_NAME, user.getGroupName());
        values.put(KEY_POINTS, user.getPointAmount());
        values.put(KEY_TITLE, user.getTitle());
        db.update(USER_INFO_TABLE, values, KEY_NAME + " = ?",
        new String[]{String.valueOf(user.getUsername())});
        return true;
    }

    public boolean deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_INFO_TABLE, KEY_NAME + " = ?",
        new String[] { String.valueOf(user.getUsername()) });
        return true;
    }

    public Integer deleteUser (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_INFO_TABLE,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
}

