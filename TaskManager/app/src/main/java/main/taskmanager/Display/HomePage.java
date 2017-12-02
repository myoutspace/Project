package main.taskmanager.Display;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        userList = databaseHelper.getAllActiveUsers();
        userListAdaptor = new MainDrawerListAdapter(this, R.layout.drawer_list_item,userList);

        // Set the adapter for the list view
        mDrawerList.setAdapter(userListAdaptor);

        mDrawerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long
                    l) {
                final User user = userList.get(i);
                final View userDialogView = inflater.inflate(R.layout.dialog_enter_group_name, null);
                dialog.setView(userDialogView);
                final EditText editText = (EditText) userDialogView.findViewById(R.id.editTxtPass);
                editText.setHint("Password");
                dialog.setTitle("Enter password of the user to delete.");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(user.getPassword().equals(editText.getText().toString())){
                            databaseHelper.deleteUser(user);
                            Toast.makeText(getApplicationContext(), "User deleted",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Wrong password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });

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
                    final User user = databaseHelper.getUser(task.getUserPost());
                    final View dialogView = inflater.inflate(R.layout.dialog_enter_group_name, null);
                    dialog.setView(dialogView);
                    final EditText editText = (EditText) dialogView.findViewById(R.id.editTxtPass);
                    editText.setHint("Password");
                    dialog.setTitle("Enter password of the user that created the task.");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(user.getPassword().equals(editText.getText().toString())){
                                databaseHelper.deleteTask(task);
                                Toast.makeText(getApplicationContext(), "Task deleted",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Wrong password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
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
            case R.id.action_documentation:
                intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://rukazana.com"));
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
