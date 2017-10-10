package main.taskmanager.Display;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

import main.taskmanager.R;
import main.taskmanager.database.*;
import main.taskmanager.javaActions.*;

public class GroupConfirmation extends AppCompatActivity {

    public static String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_confirmation);
        TextView textView = (TextView) findViewById(R.id.txtViewShwMssg);
        groupName = getIntent().getStringExtra(CreateGroup.GROUP_NAME);
        textView.setText("Your group name is "  + groupName + ", click OK to confirm!");
    }

    public void onConfirmation(View view){
        startActivity(new Intent(this, CreateUsers.class));
        UserInfoDB db = new UserInfoDB(this);
        LinkedList<User> userList = new LinkedList<User>();
        Group group = new Group(userList, groupName , "200");
        //db.addGroup(group);
    }

    public void onCancel(View view){
        startActivity(new Intent(this, CreateGroup.class));
    }
}
