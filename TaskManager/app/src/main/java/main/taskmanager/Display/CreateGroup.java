package main.taskmanager.Display;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.*;

public class CreateGroup extends AppCompatActivity {
    AlertDialog createAlert;
    boolean inPop = false;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        database = DatabaseHelper.getInstance(getApplicationContext());
    }

    public void onCreateGroup(View view) {
        EditText group = (EditText) findViewById(R.id.edtTxtGrpName);
        final String groupName = group.getText().toString();
        final Intent intent = new Intent(this, DisplayUser.class);
        intent.putExtra("groupName", groupName);
        ArrayList<String> groups = database.getAllGroups();
        boolean check = false;

        if(groups.contains(groupName)){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("The group already exists, try another name.");
            dlgAlert.setTitle("Sorry");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            check = true;
        }

        if(check == false){
            createAlert = new AlertDialog.Builder(this).create();
            createAlert.setTitle("Group name is " + groupName);
            createAlert.setMessage("Is that correct?");
            createAlert.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface
                    .OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Group group = new Group(groupName);
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

    public void onGoToHome(View view){
        final Intent intent = new Intent(this, HomePage.class);
        database.setActiveGroup(((EditText) findViewById(R.id.edtTxtGrpName)).getText().toString());
        startActivity(intent);
    }
}
