package project.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
    // Creating tables again
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUserName()); // User Name
        values.put(KEY_NAME, user.getAddress()); // User Phone Number
        values.put(KEY_GROUP_NAME, user.getAddress()); // User Phone Number
        values.put(KEY_PASSWORD, user.getPoints()); // User Phone Number
        values.put(KEY_POINTS, user.getPoints()); // User Phone Number
        values.put(KEY_TITLE, user.getPoints()); // User Phone Number
        // Inserting Row
        db.insert(TABLE_USER_INFO, null, values);
        db.close(); // Closing database connection
    }
}
