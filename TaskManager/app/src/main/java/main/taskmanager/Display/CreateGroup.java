package main.taskmanager.Display;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.*;

public class CreateGroup extends AppCompatActivity {
    AlertDialog createAlert;
    DatabaseHelper database;

    //The Create group class is only a text input that creates the group name
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        database = DatabaseHelper.getInstance(getApplicationContext());
        final EditText edittext = (EditText) findViewById(R.id.edtTxtGrpName);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    createGroup();
                    return true;
                }
                return false;
            }
        });
    }

    //The method that is called when the create group is pressed.
    public void createGroup() {
        EditText group = (EditText) findViewById(R.id.edtTxtGrpName);
        final String groupName = group.getText().toString();
        final Intent intent = new Intent(this, DisplayUser.class);
        ArrayList<String> groups = database.getAllGroups();
        Bundle dataBundle = new Bundle();
        dataBundle.putString("groupName", group.getText().toString().toLowerCase());
        intent.putExtras(dataBundle);

        //If this group name already exists create a dialog to tell the user.
        if (groups.contains(SimpleAction.capitalizeString(groupName))) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("The group already exists, try another name.");
            dlgAlert.setTitle("Sorry");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        else{
            //Group name cannot be empty or smaller then 2 letters
            if(groupName.trim().equalsIgnoreCase("")){
                group.setError("This field can not be blank");
            } else if(groupName.length() < 2){
                group.setError("The group name has to be at least 2 letters");
            } else{
                //Confirmation dialog
                createAlert = new AlertDialog.Builder(this).create();
                createAlert.setTitle("Group name is " + SimpleAction.capitalizeString(groupName));
                createAlert.setMessage("Is that correct?");
                createAlert.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Group group = new Group(groupName.toLowerCase());
                        database.addGroup(group);
                        database.setActiveGroup(group.getGroupName());
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
    }

    public void onCreateGroup(View view) {
        createGroup();
    }
}
