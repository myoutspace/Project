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

public class UserInfoDB extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "registrationInfo";
    // User table name
    private static final String TABLE_USER_INFO = "userInfo";
    private static final String TABLE_GROUP_INFO = "groupInfo";
    // User Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GROUP_NAME = "group";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POINTS = "points";

    public UserInfoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_INFO_TABLE = "CREATE TABLE " + TABLE_USER_INFO  + "("+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," +
                                        KEY_PASSWORD + " TEXT," + KEY_GROUP_NAME + " TEXT," + KEY_POINTS + "TEXT," + KEY_TITLE + "TEXT" + ")";
        db.execSQL(CREATE_USER_INFO_TABLE);
        db.execSQL("CREATE TABLE " + TABLE_GROUP_INFO  + "("+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_GROUP_NAME + " TEXT" +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_INFO);
    // Creating tables again
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername()); // User Name
        values.put(KEY_GROUP_NAME, user.getGroupName()); // User Group
        values.put(KEY_PASSWORD, user.getPassword()); // User password
        values.put(KEY_POINTS, user.getPointAmount()); // User points
        values.put(KEY_TITLE, user.getTitle()); // User title
        db.insert(TABLE_USER_INFO, null, values); // Inserting Row
        db.close(); // Closing database connection
    }

    public void addGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_NAME, group.getName()); // User Name
        db.insert(TABLE_GROUP_INFO, null, values); // Inserting Row
        db.close(); // Closing database connection
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER_INFO, new String[]{KEY_NAME, KEY_PASSWORD, KEY_GROUP_NAME, KEY_POINTS, KEY_TITLE}, KEY_NAME+ "=?",
        new String[]{String.valueOf(username)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(cursor.getString(0), cursor.getString(3), cursor.getString(1), cursor.getString(4), cursor.getString(2));
        db.close();
        return user; // return User
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>(); // Select All Query

        String selectQuery = "SELECT * FROM " + TABLE_USER_INFO;

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
        db.close();
        return userList; // return user list
    }

    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        return cursor.getCount(); // return count
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_GROUP_NAME, user.getGroupName());
        values.put(KEY_POINTS, user.getPointAmount());
        values.put(KEY_TITLE, user.getTitle());
        return db.update(TABLE_USER_INFO, values, KEY_NAME + " = ?",
        new String[]{String.valueOf(user.getUsername())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_INFO, KEY_NAME + " = ?",
        new String[] { String.valueOf(user.getUsername()) });
        db.close();
    }    
}
