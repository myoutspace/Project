package main.taskmanager.Display;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.User;

// https://www.anintegratedworld.com/creating-a-simple-navigation-drawer-in-android/
// https://android.jlelse.eu/android-adding-badge-or-count-to-the-navigation-drawer-84c93af1f4d9

public class HomePage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter mStringAdaptor;
    private ListAdapter userListAdaptor;
    private String[] mStringOfPlanets;
    private List<User> userList;

    private String[] stringTaskList;
    private ArrayAdapter contentAdapter;
    private ListView contentList;

    private String groupName;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        groupName = getIntent().getStringExtra("groupName");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        contentList = (ListView) findViewById(R.id.TaskList);


        //For now the list is fixed since there is no database functions made for it yet.
        mStringOfPlanets = this.getResources().getStringArray(R.array.planets_array);
        mStringAdaptor = new ArrayAdapter<>(this, R.layout.drawer_list_item, mStringOfPlanets);

        //To be used when the database works
        //userList = databaseHelper.getAllUserFromGroup(groupName);
        //userListAdaptor = new ArrayAdapter<User>(this, R.layout.drawer_list_item, userList);

        // Set the adapter for the list view
        mDrawerList.setAdapter(mStringAdaptor);


        //For now the list for the tasks is fixed as there are no database methods implemented yet.
        stringTaskList = this.getResources().getStringArray(R.array.list_chapters);
        contentAdapter = new ArrayAdapter<>(this, R.layout.task_list_item, stringTaskList);

        //To be used when the database works
        //stringTaskList = databaseHelper.getTasksFromGroup(groupName);
        //contentAdapter = new ArrayAdapter(this, R.layout,activity_home_page, contentTaskList);

        contentList.setAdapter(contentAdapter);
    }
}
