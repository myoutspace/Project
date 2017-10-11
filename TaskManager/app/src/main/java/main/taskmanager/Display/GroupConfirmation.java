package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import main.taskmanager.*;

public class GroupConfirmation extends AppCompatActivity {
    private static final String THE_GROUP_NAME = "groupName";
    public static String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_confirmation);
        TextView textView = (TextView) findViewById(R.id.txtViewShwMssg);
        groupName = getIntent().getStringExtra(THE_GROUP_NAME);
        textView.setText("Your group name is "  + groupName + ", click OK to confirm!");
    }

    public void onConfirmation(View view){
        Intent intent = new Intent(this, CreateUsers.class);
        intent.putExtra(THE_GROUP_NAME, groupName);
        startActivity(intent);
    }

    public void onCancel(View view){
        startActivity(new Intent(this, CreateGroup.class));
    }
}
