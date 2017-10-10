package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import main.taskmanager.*;
import main.taskmanager.database.*;

public class GroupConfirmation extends AppCompatActivity {
    private UserInfoDB db;
    public static String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_confirmation);
        TextView textView = (TextView) findViewById(R.id.txtViewShwMssg);
        groupName = getIntent().getStringExtra("groupName");
        textView.setText("Your group name is "  + groupName + ", click OK to confirm!");
        db = new UserInfoDB(this);
    }

    public void onConfirmation(View view){
        Intent intent = new Intent(this, CreateUsers.class);
        intent.putExtra("groupName", groupName);
        startActivity(intent);
        db.addGroup(groupName);
    }

    public void onCancel(View view){
        startActivity(new Intent(this, CreateGroup.class));
    }
}
