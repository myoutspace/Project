package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper database;
    ArrayList<String> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(this);
        groups = database.getAllGroups();
    }

    public void onCreateGroup(View view){
        if(groups.isEmpty())
            startActivity(new Intent(this, CreateGroup.class));
        else
            startActivity(new Intent(this, HomePage.class));
    }
}
