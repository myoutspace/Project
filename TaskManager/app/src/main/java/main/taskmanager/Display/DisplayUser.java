package main.taskmanager.Display;

import android.os.Bundle;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.SimpleAction;
import main.taskmanager.javaActions.User;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        name = (TextView) findViewById(R.id.editTextName);
        setClickEvent(name);
        title = (TextView) findViewById(R.id.editTextTitle);
        pass = (TextView) findViewById(R.id.editTextPass);
        setClickEvent(pass);

        database = DatabaseHelper.getInstance(getApplicationContext());

        groupName = database.getActiveGroup();

        Bundle extras = getIntent().getExtras();

        if (extras.getString("name") != null) {
            String value = extras.getString("name");
            users = database.getAllActiveUsers();
            if (!users.isEmpty()) {
                User user = null;
                for (User u : users) {
                    if (u.getUsername().equals(value))
                        user = u;
                }

                String name1 = user.getUsername();
                String title1 = user.getTitle();

                ImageButton b = (ImageButton) findViewById(R.id.btnAdd);
                b.setVisibility(View.INVISIBLE);

                TextView passText = (TextView) findViewById(R.id.textView4);
                passText.setVisibility(View.INVISIBLE);

                name.setText((CharSequence) SimpleAction.capitalizeString(name1));
                name.setFocusable(false);
                name.setClickable(false);

                title.setText((CharSequence) title1);
                title.setFocusable(false);
                title.setClickable(false);

                pass.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("previousActivity") != null) {
            super.onBackPressed();
        } else {

        }
    }

    public void add() {
        Intent intent = new Intent(this, CreateUsers.class);
        User user = new User(name.getText().toString().toLowerCase(), 500, pass.getText()
                .toString(), title.getText().toString(), groupName);
        ArrayList<User> users = database.getAllActiveUsers();
        ArrayList<String> usernames = new ArrayList<String>();

        //A REVOIR, (le check est pas efficasse)
        for (User u : users) {
            usernames.add(u.getUsername());
        }

        if (usernames.contains(user.getUsername().toLowerCase())) {
            Toast.makeText(this.getApplicationContext(), "The user already exists.",
                    Toast.LENGTH_LONG).show();
        } else if (name.getText().toString().trim().equalsIgnoreCase("") ||
                name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this.getApplicationContext(), "View the errors!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getApplicationContext(), "User added succesfully!",
                    Toast.LENGTH_SHORT).show();
            database.addUser(user);

            String previousActivity = getIntent().getExtras().getString("previousActivity");
            if (previousActivity != null && previousActivity.equals("HomePage")) this.finish();
            else startActivity(intent);
        }
    }

    public void setClickEvent(View view) {
        final EditText editText = ((EditText) view);
        view.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if (editText.getText().toString().trim().equalsIgnoreCase("")) {
                        editText.setError("This field can not be blank");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void addUser(View view) {
        add();
    }
}
