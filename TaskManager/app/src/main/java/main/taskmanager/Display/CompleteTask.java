package main.taskmanager.Display;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.Group;
import main.taskmanager.javaActions.SimpleAction;
import main.taskmanager.javaActions.Task;
import main.taskmanager.javaActions.User;

public class CompleteTask extends AppCompatActivity {

    private static String postedBy;
    private static int points;
    private static String tag;
    private static Spinner completedBy;
    private static String description;
    private static String res;
    private static DatabaseHelper database;
    private static ArrayList<Task> tasks;
    private static Task task;

    //Activity when completing a class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_task);
        database = DatabaseHelper.getInstance(getApplicationContext());
        ArrayList<User> users = database.getAllActiveUsers();
        ArrayList<String> usersArray = new ArrayList<String>();
        tasks = database.getAllActiveTasks();

        for (User u : users) {
            usersArray.add(SimpleAction.capitalizeString(u.getUsername()));
        }

        completedBy = (Spinner) findViewById(R.id.spinnerCompleteTask);
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_spinner_dropdown_item, usersArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        completedBy.setAdapter(adapter);

        postedBy = getIntent().getStringExtra("from");
        points = getIntent().getIntExtra("amount", 0);
        tag = getIntent().getStringExtra("tag");
        description = getIntent().getStringExtra("desc");
        res = getIntent().getStringExtra("res");
        TextView poster = (TextView) findViewById(R.id.postingUser);
        TextView amount = (TextView) findViewById(R.id.pointAmount);
        TextView theTag = (TextView) findViewById(R.id.tag);
        TextView theDescription = (TextView) findViewById(R.id.taskDescription2);
        TextView resources = (TextView) findViewById(R.id.res);
        poster.setText(SimpleAction.capitalizeString(postedBy));
        amount.setText(Integer.toString(points));
        theTag.setText(SimpleAction.capitalizeString(tag));
        theDescription.setText(description);
        resources.setText(res);
        task = new Task(postedBy, points, tag, description);
    }

    //Method called when completing a task (with the button)
    public void onCompleteTask(View view){
        final User userPost = database.getUser(postedBy);
        final User userComplete = database.getUser(((Spinner) findViewById(R.id
                .spinnerCompleteTask)).
                getSelectedItem().toString().toLowerCase());

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_enter_group_name, null);
        //Creates the dialog to check the password to complete the task
        dialog.setView(dialogView);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editTxtPass);
        editText.setHint("Password");
        dialog.setTitle("Enter password of the user that created the task.");
        dialog.setMessage("password");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(userPost.getPassword().equals(editText.getText().toString())){
                    database.deleteTask(task);
                    userComplete.addPoints(points);
                    database.updateUser(userComplete);
                    Toast.makeText(getApplicationContext(), "Task completed",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Method of the button to modify the task, starts the create task activity
    public void onEditTask(View view){
        final User userPost = database.getUser(postedBy);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_enter_group_name, null);
        dialog.setView(dialogView);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editTxtPass);
        editText.setHint("Password");
        dialog.setTitle("Enter password of the user that created the task.");
        dialog.setMessage("password");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(userPost.getPassword().equals(editText.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(), CreateTask.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("tag", tag);
                    dataBundle.putInt("amount", points);
                    dataBundle.putString("from", postedBy);
                    dataBundle.putString("desc", description);
                    dataBundle.putString("previousActivity","CompleteTask");
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
