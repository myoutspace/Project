package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.os.Handler;
import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper database;
    ArrayList<String> groups;
    private static int SPLAH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(this);
        groups = database.getAllGroups();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(database.getActiveGroup() == null)
                    startActivity(new Intent(MainActivity.this, CreateGroup.class));
                else
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                finish();
            }
        },SPLAH_TIME_OUT);
    }

    /*
    public void onCreateGroup(View view){
        if(database.getActiveGroup() == null)
            startActivity(new Intent(this, CreateGroup.class));
        else
            startActivity(new Intent(this, HomePage.class));

    }
    */
}
