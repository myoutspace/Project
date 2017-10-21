package main.taskmanager.Display;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

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
        database = new DatabaseHelper(this.getApplicationContext());
    }

    public void onCreateGroup(View view) {
        final Intent intent = new Intent(this, CreateUsers.class);
        EditText group = (EditText) findViewById(R.id.edtTxtGrpName);
        final String groupName = group.getText().toString();
        intent.putExtra("groupName", groupName);
        createAlert = new AlertDialog.Builder(this).create();
        createAlert.setTitle("Group name is " + groupName);
        createAlert.setMessage("Is that correct?");
        createAlert.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Group group = new Group(groupName);
                database.addGroup(group);
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
