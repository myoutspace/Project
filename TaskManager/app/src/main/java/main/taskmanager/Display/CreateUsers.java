package main.taskmanager.Display;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.Group;
import main.taskmanager.javaActions.SimpleAction;
import main.taskmanager.javaActions.User;

public class CreateUsers extends AppCompatActivity {
    public static String groupName;
    private ListView listView;
    DatabaseHelper database;
    AlertDialog createAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_users);
        TextView textView = (TextView) findViewById(R.id.txtViewShwMssg);
        database = DatabaseHelper.getInstance(getApplicationContext());
        groupName = database.getActiveGroup();
        textView.setText("Users in " + SimpleAction.capitalizeString(groupName));
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        database.setActiveUsers(null);
        final ArrayList<User> users = database.getAllActiveUsers();
        final ArrayList<String> usersName = new ArrayList<String>();

        for(User user : users){
            usersName.add(SimpleAction.capitalizeString(user.getUsername()));
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, usersName);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String name = usersName.get(arg2);
                Bundle dataBundle = new Bundle();
                dataBundle.putString("name", name);
                dataBundle.putInt("id", arg2);
                Intent intent = new Intent(getApplicationContext(),DisplayUser.class);
                intent.putExtras(dataBundle);
                startActivity(intent);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
                    id) {
                final User user = users.get(position);
                deleteDialog.setTitle("Are you sure you want to delete " + SimpleAction.capitalizeString(user.getUsername()) +
                        "?");
                deleteDialog.setButton(deleteDialog.BUTTON_POSITIVE, "Yes", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteUser(user);
                        Intent reloadPage = new Intent(getApplicationContext(), CreateUsers.class);
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

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.Add_User:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                dataBundle.putString("groupName", groupName);

                Intent intent = new Intent(getApplicationContext(),DisplayUser.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addPerson(View view) {
        Intent intent = new Intent(this, DisplayUser.class);
        intent.putExtra("previousActivity", "CreateUser");
        startActivity(intent);
    }

    public void onConfirmGroup(View view) {
        final Intent intent = new Intent(this, HomePage.class);

        createAlert = new AlertDialog.Builder(this).create();
        createAlert.setTitle("Confirm users in " + SimpleAction.capitalizeString(groupName));
        createAlert.setMessage("Are you sure to create a group with these users?");
        createAlert.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(intent);
            }
        });

        createAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createAlert.dismiss();
            }
        });
        createAlert.show();
    }
}
