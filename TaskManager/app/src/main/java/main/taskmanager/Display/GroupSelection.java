// for dropdown on longpress : https://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/

package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.GroupSelectionAdapter;

public class GroupSelection extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView groupListDisplay;
    ArrayList<String> groupList;
    GroupSelectionAdapter groupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());

        groupListDisplay = (ListView) findViewById(R.id.groupList);

        groupList = databaseHelper.getAllGroups();
        groupListAdapter = new GroupSelectionAdapter(this,groupList,databaseHelper);

        groupListDisplay.setAdapter(groupListAdapter);

    }

    public void onAddGroup (View v){
        Intent addGroup = new Intent(this,CreateGroup.class);
        this.startActivity(addGroup);
    }



}
