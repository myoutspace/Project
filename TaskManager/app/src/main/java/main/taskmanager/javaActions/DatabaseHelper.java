package main.taskmanager.javaActions;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Production on 10/13/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Name
    private static final String DB_NAME = "userInfo";

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_GROUPS = "groups";
    private static final String TABLE_TASKS = "tasks";

    //Same colum names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    //Users Table columns
    private static final String KEY_TITLE = "title";
    private static final String KEY_PASSWORD = "pass";
    private static final String KEY_POINTS = "points";  // Same column name also in tasks table
    private static final String KEY_GROUP = "team";

    //Tasks Table columns
    private static final String KEY_USER_POST = "userpost";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TAG = "tag";


    // Static strings to save data

    //Table create Statements
    private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUPS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT" + ")";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, " + KEY_GROUP + " TEXT, "
            + KEY_PASSWORD + " TEXT, " + KEY_POINTS + " TEXT, " + KEY_TITLE + " TEXT)";

    private static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASKS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_USER_POST + " TEXT, " + KEY_POINTS + "" +
            " TEXT, "
            + KEY_TAG + " TEXT, " + KEY_DESCRIPTION + " TEXT, " + KEY_GROUP + " TEXT)";

    private static String activeGroup;
    private static ArrayList<User> activeUsers;
    private static ArrayList<Task> activeTasks;
    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_GROUP);
        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(CREATE_TABLE_TASK);
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, int OldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(database);
    }

    public void addGroup(Group group) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.getGroupName());
        long insert = database.insert(TABLE_GROUPS, null, values);
    }


    public void addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername());
        values.put(KEY_TITLE, user.getTitle());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_POINTS, Integer.toString(user.getPointAmount()));
        values.put(KEY_GROUP, user.getGroupName());
        long insert = database.insert(TABLE_USERS, null, values);

        if (activeUsers != null) activeUsers.add(user);
    }

    public void addTask(Task task, String groupName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_POST, task.getUserPost());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_TAG, task.getTag());
        values.put(KEY_POINTS, Integer.toString(task.getPointAmount()));
        values.put(KEY_GROUP, groupName);
        long insert = database.insert(TABLE_TASKS, null, values);

        if (activeTasks != null) activeTasks.add(task);
    }

    public ArrayList<User> getAllActiveUsers() {
        if (activeUsers == null) {
            activeUsers = new ArrayList<User>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users where " + KEY_GROUP + " = '" +
                    getActiveGroup() + "'", null);

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                User user = new User(cursor.getString(cursor.getColumnIndex(KEY_NAME)), Integer
                        .parseInt(cursor
                        .getString(cursor.getColumnIndex(KEY_POINTS))), cursor
                        .getString(cursor.getColumnIndex(KEY_TITLE)), cursor
                        .getString(cursor.getColumnIndex(KEY_PASSWORD)), cursor
                        .getString(cursor.getColumnIndex(KEY_GROUP)));
                activeUsers.add(user);
                cursor.moveToNext();
            }
        }
        return activeUsers;
    }

    /*
        Before using this method, we need to discuss how we want to work with the static variables,
     */

    public ArrayList<Task> getAllActiveTasks(String groupName) {
        if (activeTasks == null) {
            activeTasks = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + TABLE_TASKS + " where " + KEY_GROUP +
                    " = ?", new String[]{groupName});

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                Task task = new Task(cursor.getString(cursor.getColumnIndex(KEY_USER_POST)),
                        Integer.parseInt(cursor
                        .getString(cursor.getColumnIndex(KEY_POINTS))), cursor
                        .getString(cursor.getColumnIndex(KEY_TAG)), cursor
                        .getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                activeTasks.add(task);
                cursor.moveToNext();
            }
        }

        return activeTasks;
    }

    public ArrayList<String> getAllGroups() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from groups", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            String groupName = (cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            array_list.add(groupName);
            cursor.moveToNext();
        }

        return array_list;
    }

    public Integer deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USERS, KEY_NAME + "= ? AND " + KEY_GROUP + " = ?", new
                String[]{user.getUsername(), user.getGroupName()});
    }

    public Integer deleteTask(String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        int index = findTaskIndex(tag);
        activeTasks.remove(index);

        return db.delete(TABLE_TASKS, KEY_TAG + "= ? AND " + KEY_GROUP + " = ?",
                new String[]{tag, getActiveGroup()});
    }

    private Integer findTaskIndex(String tag){
        int i = 0;
        if(activeTasks != null && !activeTasks.isEmpty()){
            while(!activeTasks.get(i).getTag().equals(tag)) {
                i++;
            }
        }

        return i;
    }

    public Integer deleteTask(String tag, String groupName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TASKS, KEY_GROUP + "= ? AND " + KEY_TAG + " = ?", new
                String[]{groupName, tag});
    }

    public Integer deleteGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_GROUP + "= ? ", new String[]{group.getGroupName()});
        db.delete(TABLE_TASKS, KEY_GROUP + "= ? ", new String[]{group.getGroupName()});
        return db.delete(TABLE_GROUPS, KEY_NAME + "= ? ", new String[]{group.getGroupName()});
    }

    public String getActiveGroup() {
        return activeGroup;
    }

    public void setActiveGroup(String activeGroup) {
        DatabaseHelper.activeGroup = activeGroup;
    }

    public void closeConnection() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }

    public User getUser(String name) {
        for(User user : activeUsers){
            if (user.getUsername().equals(name)) return user;
        }

        return null;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_POINTS, user.getPointAmount());
        db.update(TABLE_USERS, contentValues,
                "name = ?", new String[]{user.getUsername()});
    }

}
