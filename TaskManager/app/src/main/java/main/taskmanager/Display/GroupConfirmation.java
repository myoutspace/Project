package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import main.taskmanager.*;
import main.taskmanager.javaActions.*;

public class GroupConfirmation extends AppCompatActivity {
    private static final String THE_GROUP_NAME = "groupName";
    private static String groupName;
    private static DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_confirmation);
        TextView textView = (TextView) findViewById(R.id.txtViewShwMssg);
        groupName = getIntent().getStringExtra(THE_GROUP_NAME);
        textView.setText("Your group name is " + groupName + ", click OK to confirm!");
        database = new DatabaseHelper(this.getApplicationContext());
    }

    public void onConfirmation(View view) {
        Intent intent = new Intent(this, CreateUsers.class);
        intent.putExtra(THE_GROUP_NAME, groupName);
        Group group = new Group(groupName);
        startActivity(intent);
        database.addGroup(group);
    }

    public void onCancel(View view) {
        startActivity(new Intent(this, CreateGroup.class));
    }
}
