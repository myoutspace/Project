package main.taskmanager.javaActions;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Production on 10/13/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Name
    private static final String DB_NAME = "userInfo";

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_GROUPS = "groups";

    //Same colum names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    //Users Table columns
    private static final String KEY_TITLE = "title";
    private static final String KEY_PASSWORD = "pass";
    private static final String KEY_POINTS = "points";
    private static final String KEY_CREATED_AT = "createdAt";
    private static final String KEY_GROUP = "team";

    // Static strings to save data

    //Table create Statements
    private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUPS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT" + ")";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, " + KEY_GROUP + " TEXT, "
            + KEY_PASSWORD + " TEXT, " + KEY_POINTS + " TEXT, " + KEY_TITLE + " TEXT)";

    private static String activeGroup;
    private static ArrayList<User> activeUsers;
    private static ArrayList<Task> activeTasks;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_GROUP);
        database.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int OldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(database);
    }

    public void addGroup(Group group) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.getName());
        long insert = database.insert(TABLE_GROUPS, null, values);
    }

    public void addUser(User user, String dateCreation) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername());
        values.put(KEY_TITLE, user.getTitle());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_POINTS, user.getPointAmount());
        values.put(KEY_GROUP, user.getGroupName());
        long insert = database.insert(TABLE_USERS, null, values);


    }

    public User getUser(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_ID + " = " + id;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) cursor.moveToFirst();
        else return null;

        return new User(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor
                .getString(cursor.getColumnIndex(KEY_POINTS)), cursor
                .getString(cursor.getColumnIndex(KEY_TITLE)), cursor
                .getString(cursor.getColumnIndex(KEY_PASSWORD)), cursor
                .getString(cursor.getColumnIndex(KEY_GROUP)));

    }

    public ArrayList<User> getAllUsers(String groupName) {
        if(activeUsers == null) {
            activeUsers = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users where team = '" + groupName + "'", null);

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                User user = new User(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor
                        .getString(cursor.getColumnIndex(KEY_POINTS)), cursor
                        .getString(cursor.getColumnIndex(KEY_TITLE)), cursor
                        .getString(cursor.getColumnIndex(KEY_PASSWORD)), cursor
                        .getString(cursor.getColumnIndex(KEY_GROUP)));
                activeUsers.add(user);
                cursor.moveToNext();
            }
        }

        return activeUsers;
    }

    public ArrayList<String> getAllGroups() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from groups", null );
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            String groupName = (cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            array_list.add(groupName);
            cursor.moveToNext();
        }

        return array_list;
    }

    public Integer deleteUser (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users", "id = ? ", new String[] { Integer.toString(id) });
    }


}
