package main.taskmanager.javaActions;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String KEY_GROUP = "group";

    //Table create Statements
    private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUPS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT" + ")";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID

            + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT," + KEY_GROUP + " TEXT," + KEY_TITLE +
            " TEXT," + KEY_PASSWORD + " TEXT," + KEY_POINTS + " TEXT," + KEY_CREATED_AT + " TEXT"
            + ")";




    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_TABLE_GROUP);
        database.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int OldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(database);
    }

    public void addGroup(Group group){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.getName());
        long insert = database.insert(TABLE_GROUPS, null, values);
    }

    public void addUser(User user, String dateCreation){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername());
        values.put(KEY_TITLE, user.getTitle());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_POINTS, user.getPointAmount());
        values.put(KEY_GROUP, user.getGroupName());
        values.put(KEY_CREATED_AT, dateCreation);
        long insert = database.insert(TABLE_GROUPS, null, values);
    }

}
