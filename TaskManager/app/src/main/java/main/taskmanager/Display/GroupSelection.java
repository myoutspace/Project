package main.taskmanager.Display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;

public class GroupSelection extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
    }




}
