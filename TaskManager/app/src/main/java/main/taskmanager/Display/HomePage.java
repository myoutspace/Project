package main.taskmanager.Display;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import main.taskmanager.R;
import main.taskmanager.javaActions.*;


// https://www.anintegratedworld.com/creating-a-simple-navigation-drawer-in-android/
// https://android.jlelse.eu/android-adding-badge-or-count-to-the-navigation-drawer-84c93af1f4d9

public class HomePage extends AppCompatActivity {
    private ListView mDrawerList;
    private MainDrawerListAdapter userListAdaptor;
    private ArrayList<User> userList;
    private TaskListAdapter contentAdapter;
    private ListView contentList;
    private String groupName;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        setContentView(R.layout.activity_home_page);
        groupName = databaseHelper.getActiveGroup();
        getSupportActionBar().setTitle(SimpleAction.capitalizeString(groupName));
        databaseHelper.setActiveTasks(null);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        contentList = (ListView) findViewById(R.id.TaskList);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        userList = databaseHelper.getAllActiveUsers();
        userListAdaptor = new MainDrawerListAdapter(this, R.layout.drawer_list_item,userList);

        // Set the adapter for the list view
        mDrawerList.setAdapter(userListAdaptor);
        View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_list_footer, null, false);
        mDrawerList.addFooterView(footerView);

        final ArrayList<Task> taskList;
        taskList = databaseHelper.getAllActiveTasks();

        if (taskList.isEmpty()) {
            TextView noTask = (TextView) findViewById(R.id.txtViewNoTask);
            contentList.setVisibility(View.GONE);
            noTask.setText("There are currently no tasks available");
            noTask.setVisibility(View.VISIBLE);
        } else {
            contentAdapter = new TaskListAdapter(this, taskList);

            contentList.setAdapter(contentAdapter);

            contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), CompleteTask.class);
                    Task task = taskList.get(i);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("from", task.getUserPost());
                    dataBundle.putString("tag", task.getTag());
                    dataBundle.putString("desc", task.getDescription());
                    dataBundle.putInt("amount", task.getPointAmount());
                    intent.putExtras(dataBundle);
                    startActivityForResult(intent, 1);
                }
            });

            contentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long
                        l) {
                    final Task task = taskList.get(i);
                    deleteDialog.setTitle("Are you sure you want to delete with the tag " + task
                            .getTag() +
                            "?");
                    deleteDialog.setButton(deleteDialog.BUTTON_POSITIVE, "Yes", new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHelper.deleteTask(task);
                            Intent reloadPage = new Intent(getApplicationContext(), HomePage.class);
                            getApplicationContext().startActivity(reloadPage);
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.action_switch_group:
                databaseHelper.setActiveTasks(null);
                databaseHelper.setActiveUsers(null);
                Intent intent = new Intent(this, GroupSelection.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addTask(View view) {
        Intent intent = new Intent(this, CreateTask.class);
        startActivityForResult(intent,1);
    }


    public void addUser(View view) {
        Intent intent = new Intent(this,DisplayUser.class);
        intent.putExtra("previousActivity", "HomePage");
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        userList = databaseHelper.getAllActiveUsers();
        userListAdaptor = new MainDrawerListAdapter(this, R.layout.drawer_list_item,userList);

        // Set the adapter for the list view
        mDrawerList.setAdapter(userListAdaptor);

        final ArrayList<Task> taskList;
        taskList = databaseHelper.getAllActiveTasks();

        contentAdapter = new TaskListAdapter(this, taskList);

        contentList.setAdapter(contentAdapter);

    }
}
