package main.taskmanager.Display;

import android.os.Bundle;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
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

import java.util.*;
import java.text.SimpleDateFormat;

import static main.taskmanager.Display.CreateUsers.groupName;

public class DisplayUser extends Activity {

    private static String groupName;
    private static TextView name;
    private static TextView title;
    private static TextView pass;
    private static DatabaseHelper database;

    /*
    Notes:
    - a mon avis avoir une méthode qui détermine le nombre d'utilisateurs dans la base de
    donnée. Ainsi lorsqu'il y a deux utilisateur on crée un nouveau xml et classe identique à
    displayUser mais avec un boutton secondaire pour continuer dans la page d'assigner une tache

    - aussi au lieu d'utiliser intent.putExtra, on cherche directement l'information de la base
    de données
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        name = (TextView) findViewById(R.id.editTextName);
        title = (TextView) findViewById(R.id.editTextTitle);
        pass = (TextView) findViewById(R.id.editTextPass);
        groupName = getIntent().getStringExtra("groupName");
        database = new DatabaseHelper(this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.create_users, menu);
            } else {
                getMenuInflater().inflate(R.menu.create_users, menu);
            }
        }
        return true;
    }

    public void addUser(View view) {
        Intent intent = new Intent(this, CreateUsers.class);
        intent.putExtra("groupName", groupName);
        User user = new User(name.getText().toString(), "200", pass.getText().toString(), title
                .getText().toString(), groupName);
        String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        //I comment cause causing problems
        //database.addUser(user, date);
        startActivity(intent);
    }
}