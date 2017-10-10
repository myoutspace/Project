package main.taskmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.provider.BaseColumns;

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

    private String CREATE_USER_INFO_TABLE = "CREATE TABLE " + TABLE_USER_INFO  + "("+ UserInfoTable.UserInfo.KEY_ID + " INTEGER PRIMARY KEY," + UserInfoTable.UserInfo.KEY_NAME + " TEXT," +
            UserInfoTable.UserInfo.KEY_PASSWORD + " TEXT," + UserInfoTable.UserInfo.KEY_GROUP_NAME + " TEXT," + UserInfoTable.UserInfo.KEY_POINTS + "TEXT," + UserInfoTable.UserInfo.KEY_TITLE + "TEXT" + ")";

    private String CREATE_GROUP_INFO = "CREATE TABLE " + TABLE_GROUP_INFO  + "("+ UserInfoTable.UserInfo.KEY_ID + " INTEGER PRIMARY KEY," + UserInfoTable.UserInfo.KEY_GROUP_NAME + " TEXT" +")";
    // User Table Columns names

    public final class UserInfoTable {
        // This prevent someone to instantiate the userInfoTable class,
        // make the constructor private.
        private UserInfoTable() {}

        public class UserInfo implements BaseColumns {
            private static final String KEY_ID = "id";
            private static final String KEY_NAME = "name";
            private static final String KEY_GROUP_NAME = "group";
            private static final String KEY_PASSWORD = "password";
            private static final String KEY_TITLE = "title";
            private static final String KEY_POINTS = "points";
        }
    }

    public UserInfoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_INFO_TABLE);
        db.execSQL(CREATE_GROUP_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_INFO);
    // Creating tables again
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserInfoTable.UserInfo.KEY_NAME, user.getUsername()); // User Name
        values.put(UserInfoTable.UserInfo.KEY_GROUP_NAME, user.getGroupName()); // User Group
        values.put(UserInfoTable.UserInfo.KEY_PASSWORD, user.getPassword()); // User password
        values.put(UserInfoTable.UserInfo.KEY_POINTS, user.getPointAmount()); // User points
        values.put(UserInfoTable.UserInfo.KEY_TITLE, user.getTitle()); // User title
        db.insert(TABLE_USER_INFO, null, values); // Inserting Row
        db.close(); // Closing database connection
    }

    public void addGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserInfoTable.UserInfo.KEY_GROUP_NAME, group.getName()); // User Name
        db.insert(TABLE_GROUP_INFO, null, values); // Inserting Row
        db.close(); // Closing database connection
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER_INFO, new String[]{UserInfoTable.UserInfo.KEY_NAME, UserInfoTable.UserInfo.KEY_PASSWORD, UserInfoTable.UserInfo.KEY_GROUP_NAME, UserInfoTable.UserInfo.KEY_POINTS, UserInfoTable.UserInfo.KEY_TITLE}, UserInfoTable.UserInfo.KEY_NAME+ "=?",
        new String[]{String.valueOf(username)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(cursor.getString(0), cursor.getString(3), cursor.getString(1), cursor.getString(4), cursor.getString(2));
        cursor.close();
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
        values.put(UserInfoTable.UserInfo.KEY_NAME, user.getUsername());
        values.put(UserInfoTable.UserInfo.KEY_PASSWORD, user.getPassword());
        values.put(UserInfoTable.UserInfo.KEY_GROUP_NAME, user.getGroupName());
        values.put(UserInfoTable.UserInfo.KEY_POINTS, user.getPointAmount());
        values.put(UserInfoTable.UserInfo.KEY_TITLE, user.getTitle());
        return db.update(TABLE_USER_INFO, values, UserInfoTable.UserInfo.KEY_NAME + " = ?",
        new String[]{String.valueOf(user.getUsername())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_INFO, UserInfoTable.UserInfo.KEY_NAME + " = ?",
        new String[] { String.valueOf(user.getUsername()) });
        db.close();
    }    
}
