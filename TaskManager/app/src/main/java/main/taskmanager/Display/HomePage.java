package main.taskmanager.Display;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.MainDrawerListAdapter;
import main.taskmanager.javaActions.User;

// https://www.anintegratedworld.com/creating-a-simple-navigation-drawer-in-android/
// https://android.jlelse.eu/android-adding-badge-or-count-to-the-navigation-drawer-84c93af1f4d9

public class HomePage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter mStringAdaptor;
    private ListAdapter userListAdaptor;
    private String[] mStringOfPlanets;
    private ArrayList<User> userList;

    private String[] stringTaskList;
    private ArrayAdapter contentAdapter;
    private ListView contentList;

    private String groupName;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_home_page);
        groupName = databaseHelper.getActiveGroup();
        getSupportActionBar().setTitle(groupName);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        contentList = (ListView) findViewById(R.id.TaskList);


        //To be used when the database works
        databaseHelper.addUser(new User("pat", 500, "j","leGrand",databaseHelper.getActiveGroup()),"hello");
        databaseHelper.addUser(new User("jack", 500, "j","leMoyen",databaseHelper.getActiveGroup()),"hello");
        databaseHelper.addUser(new User("max", 500, "j","lePetit",databaseHelper.getActiveGroup()),"hello");


        userList = databaseHelper.getAllActiveUsers();
        //userListAdaptor = new ArrayAdapter<User>(this,R.layout.drawer_list_item,userList);
        userListAdaptor = new MainDrawerListAdapter(this, R.layout.drawer_list_item,userList);

        // Set the adapter for the list view
        mDrawerList.setAdapter(userListAdaptor);


        //For now the list for the tasks is fixed as there are no database methods implemented yet.
        stringTaskList = this.getResources().getStringArray(R.array.list_chapters);
        contentAdapter = new ArrayAdapter<>(this, R.layout.task_list_item, stringTaskList);

        //To be used when the database works
        //stringTaskList = databaseHelper.getActiveTasks;
        //contentAdapter = new ArrayAdapter(this, R.layout.task_list_item, stringTaskList);

        contentList.setAdapter(contentAdapter);
    }
}
