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
    private static int SPLAH_TIME_OUT = 2000;

    //Main activity to display the logo and build suspense... it works everytime!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = DatabaseHelper.getInstance(getApplicationContext());
        groups = database.getAllGroups();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(groups.isEmpty())
                    startActivity(new Intent(MainActivity.this, CreateGroup.class));
                else{
                    if(database.getActiveGroup() == null)
                        startActivity(new Intent(MainActivity.this, GroupSelection.class));
                    else
                        startActivity(new Intent(MainActivity.this, HomePage.class));
                }
                finish();
            }
        },SPLAH_TIME_OUT);
    }
}
