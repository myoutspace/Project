// for dropdown on longpress : https://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/

package main.taskmanager.Display;
import android.content.*;
import android.support.v7.app.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import main.taskmanager.R;
import main.taskmanager.javaActions.*;

public class GroupSelection extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView groupListDisplay;
    ArrayList<String> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        groupListDisplay = (ListView) findViewById(R.id.groupList);
        groupList = databaseHelper.getAllGroups();

        if (groupList.isEmpty()) {
            TextView noGroup = (TextView) findViewById(R.id.txtViewNoGroup);
            groupListDisplay.setVisibility(View.GONE);
            noGroup.setText("There are currently no groups. Create one!");
            noGroup.setVisibility(View.VISIBLE);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, groupList);
        groupListDisplay.setAdapter(arrayAdapter);
        groupListDisplay.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
                    id) {
                final Group group = new Group((groupList.get(position)).toLowerCase());
                deleteDialog.setTitle("Are you sure you want to delete " + SimpleAction.capitalizeString(group.getGroupName() +
                        "?"));
                deleteDialog.setButton(deleteDialog.BUTTON_POSITIVE, "Yes", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.deleteGroup(group);
                        Intent reloadPage = new Intent(getApplicationContext(), GroupSelection.class);
                        startActivity(reloadPage);
                    }
                });
                deleteDialog.setButton(deleteDialog.BUTTON_NEGATIVE, "No", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
                return true;
            }
        });

        groupListDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String group = groupList.get(position);
                databaseHelper.setActiveGroup(group.toLowerCase());
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){

    }

    public void onAddGroup (View v){
        Intent addGroup = new Intent(this,CreateGroup.class);
        this.startActivity(addGroup);
    }



}
