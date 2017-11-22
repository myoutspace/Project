// for dropdown on longpress : https://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/

package main.taskmanager.Display;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.Group;
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

    @Override
    public void onBackPressed(){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_add_group:
                Intent addGroup = new Intent(this, CreateGroup.class);
                this.startActivity(addGroup);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onAddGroup (View v){
        Intent addGroup = new Intent(this,CreateGroup.class);
        this.startActivity(addGroup);
    }



}
