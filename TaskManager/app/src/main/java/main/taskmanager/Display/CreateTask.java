package main.taskmanager.Display;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.SimpleAction;
import main.taskmanager.javaActions.Task;
import main.taskmanager.javaActions.User;

import static android.widget.ArrayAdapter.createFromResource;

public class CreateTask extends AppCompatActivity {

    private static Spinner from;
    private static TextInputEditText tag;
    private static TextInputEditText description;
    private static TextInputEditText amount;
    private static DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        database = DatabaseHelper.getInstance(getApplicationContext());

        ArrayList<User> users = database.getAllActiveUsers();
        ArrayList<String> usersArray = new ArrayList<String>();

        for (User u : users) {
            usersArray.add(SimpleAction.capitalizeString(u.getUsername()));
        }


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_dropdown_item, usersArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        spinner.setAdapter(adapter);

        from = (Spinner) findViewById(R.id.spinner);
        tag = (TextInputEditText) findViewById(R.id.editTextTag);
        setClickEvent(tag);
        description = (TextInputEditText) findViewById(R.id.editTextDescription);
        amount = (TextInputEditText) findViewById(R.id.editTextAmount);
    }

    public void addTask(View view) {
        Integer pointsToRemove;
        try
        {
            pointsToRemove = Integer.parseInt(amount.getText().toString());
        }catch(NumberFormatException e){
            pointsToRemove = 0;
        }

        User userPost = database.getUser(from.getSelectedItem().toString().toLowerCase());
        ArrayList<Task> allTasks = database.getAllActiveTasks();

        if(userPost.getPointAmount() - pointsToRemove < 0) {
            Toast.makeText(this.getApplicationContext(), "You do not have enough points to create this task",
                    Toast.LENGTH_LONG).show();
        } else{

            Task task;
            String taskTag = tag.getText().toString().toLowerCase();
            boolean check = false;

            for (Task t : allTasks) {
                if (t.getTag().equals(taskTag))
                    check = true;
            }

            if (check){
                Toast.makeText(this.getApplicationContext(), "A task with that tag already exits!",
                        Toast.LENGTH_LONG).show();
            } else if(taskTag.trim().equalsIgnoreCase("")){
                Toast.makeText(this.getApplicationContext(), "The tag field cannot be empty!",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.getApplicationContext(), "Task added succesfully",
                        Toast.LENGTH_LONG).show();
                task = new Task(userPost.getUsername(), pointsToRemove, taskTag.toLowerCase(),
                        description.getText().toString());

                database.addTask(task,database.getActiveGroup());
                userPost.removePoints(pointsToRemove);
                database.updateUser(userPost);

                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
            }
        }
    }

    public void onCancelTask(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
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
}
