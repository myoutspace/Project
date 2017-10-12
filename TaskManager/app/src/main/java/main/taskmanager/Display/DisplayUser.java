package main.taskmanager.Display;

import android.os.Bundle;

import main.taskmanager.R;
import main.taskmanager.javaActions.User;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayUser extends Activity {

    TextView name ;
    TextView title;
    TextView pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        name = (TextView) findViewById(R.id.editTextName);
        title = (TextView) findViewById(R.id.editTextTitle);
        pass = (TextView) findViewById(R.id.editTextPass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.create_users, menu);
            } else{
                getMenuInflater().inflate(R.menu.create_users, menu);
            }
        }
        return true;
    }

    public void addUser(View view){
        Intent intent = new Intent(this, CreateUsers.class);
        startActivity(intent);
    }
}