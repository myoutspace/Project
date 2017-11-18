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
import android.widget.ImageButton;
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
    private ArrayList<User> users;
    int id_To_Update = 0;

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

        database = DatabaseHelper.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            String value = extras.getString("name");
            int id = extras.getInt("id");
            users = database.getAllActiveUsers();
            if (id > 0) {

                User user = null;
                for (User u : users) {
                    if (u.getUsername().equals(value))
                        user = u;
                }

                id_To_Update = id;
                String name1 = user.getUsername();
                String title1 = user.getTitle();
                String password = user.getPassword();

                ImageButton b = (ImageButton)findViewById(R.id.btnAdd);
                b.setVisibility(View.INVISIBLE);

                TextView passText = (TextView) findViewById(R.id.textView4);
                passText.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)name1);
                name.setFocusable(false);
                name.setClickable(false);

                title.setText((CharSequence)title1);
                title.setFocusable(false);
                title.setClickable(false);

                pass.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            if (id_To_Update > 0) {
                getMenuInflater().inflate(R.menu.display_user, menu);
            } else {
                getMenuInflater().inflate(R.menu.create_users, menu);
            }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                ImageButton b = (ImageButton)findViewById(R.id.btnAdd);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                title.setEnabled(true);
                title.setFocusableInTouchMode(true);
                title.setClickable(true);

                pass.setEnabled(true);
                pass.setFocusableInTouchMode(true);
                pass.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_User)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                database.deleteUser(name.getText().toString());
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void addUser(View view) {
        Intent intent = new Intent(this, CreateUsers.class);
        User user = new User(name.getText().toString(), 500, pass.getText().toString(), title
                .getText().toString(), groupName);
        ArrayList<User> users = database.getAllActiveUsers();
        ArrayList<String> usernames = new ArrayList<String>();

        //A REVOIR, (le check est pas efficasse)
        for (User u : users) {
            usernames.add(u.getUsername());
        }

        if (usernames.contains(user.getUsername())) {
            Toast.makeText(this.getApplicationContext(), "The user already exists.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getApplicationContext(), "User added succesfully!",
                    Toast.LENGTH_LONG).show();
            database.addUser(user);

            String previousActivity = getIntent().getExtras().getString("previousActivity");
            if(previousActivity != null &&previousActivity.equals("HomePage")) this.finish();
            else startActivity(intent);
        }
    }
}