package main.taskmanager.Display;

import android.content.Intent;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;
import main.taskmanager.R;
import main.taskmanager.javaActions.*;

public class CreateTask extends AppCompatActivity {

    private static Spinner from;
    private static TextInputEditText tag;
    private static TextInputEditText description;
    private static TextInputEditText amount;
    private static CheckBox addRessources;
    private static ArrayList<String> resources;
    private static DatabaseHelper database;
    private static User userPost;
    private static Task oldTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        database = DatabaseHelper.getInstance(getApplicationContext());
        ArrayList<User> users = database.getAllActiveUsers();
        ArrayList<String> usersArray = new ArrayList<String>();
        resources = new ArrayList<String>();
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
        addRessources = (CheckBox) findViewById(R.id.check_add_ressources);
        addRessources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), ResourceSelection.class);
                    startActivityForResult(intent, 1);
                }
            }
        });
        if(getIntent().getStringExtra("previousActivity").equals("CompleteTask")){
            ((LinearLayout) findViewById(R.id.layout_horizental_top)).setVisibility(View.GONE);
            ((Space) findViewById(R.id.spaceCreate)).setVisibility(View.VISIBLE);
            tag.setText(getIntent().getStringExtra("tag"));
            userPost = database.getUser(getIntent().getStringExtra("from"));
            userPost.addPoints(getIntent().getIntExtra("amount",0));
            description.setText(getIntent().getStringExtra("desc"));
            amount.setText(Integer.toString(getIntent().getIntExtra("amount", 0)));
            oldTask = new Task(getIntent().getStringExtra("from"), getIntent().getIntExtra("amount",
                    0), getIntent().getStringExtra("tag"), getIntent().getStringExtra("desc"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && getIntent()
                .getStringExtra("previousActivity").equals("ResourceSelection")) {
            resources = data.getStringArrayListExtra("listResource");
            Log.d("","");
        }
    }

    public void addTask(View view) {

        if(getIntent().getStringExtra("previousActivity").equals("HomePage"))
            userPost = database.getUser(from.getSelectedItem().toString().toLowerCase());

        Integer pointsToRemove;
        try
        {
            pointsToRemove = Integer.parseInt(amount.getText().toString());
        }catch(NumberFormatException e){
            pointsToRemove = 0;
        }

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

            if (check && getIntent().getStringExtra("previousActivity").equals("HomePage")){
                Toast.makeText(this.getApplicationContext(), "A task with that tag already exits!",
                        Toast.LENGTH_LONG).show();
            } else if(taskTag.trim().equalsIgnoreCase("")){
                Toast.makeText(this.getApplicationContext(), "The tag field cannot be empty!",
                        Toast.LENGTH_LONG).show();
            } else if(getIntent().getStringExtra("previousActivity").equals("CompleteTask")){
                Toast.makeText(this.getApplicationContext(), "Task updated succesfully",
                        Toast.LENGTH_LONG).show();
                task = new Task(userPost.getUsername(), pointsToRemove, taskTag.toLowerCase(),
                        description.getText().toString());
                if(taskTag.toLowerCase().equals(getIntent().getStringExtra("tag"))){
                    database.updateTask(task);
                    userPost.removePoints(pointsToRemove);
                    database.updateUser(userPost);
                }
                else{
                    String res = "";
                    if (!resources.isEmpty()) {
                        for (String s : resources) {
                            res = res + " , " + SimpleAction.capitalizeString(s);
                            Log.d("","");
                        }
                    }
                    database.deleteTask(oldTask);
                    userPost.removePoints(pointsToRemove);
                    database.addTask(task, database.getActiveGroup(), res);
                    database.updateUser(userPost);
                }
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
            }else {
                String res = "";
                if (!resources.isEmpty()) {
                    for (String s : resources) {
                        res = res + ", " + SimpleAction.capitalizeString(s);
                    }
                }
                Toast.makeText(this.getApplicationContext(), "Task added succesfully",
                        Toast.LENGTH_LONG).show();
                task = new Task(userPost.getUsername(), pointsToRemove, taskTag.toLowerCase(),
                        description.getText().toString());
                database.addTask(task, database.getActiveGroup(), res);
                userPost.removePoints(pointsToRemove);
                database.updateUser(userPost);

                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
            }
        }
    }

    public void onCancelTask(View view) {
        if(getIntent().getStringExtra("previousActivity").equals("HomePage")){
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, CompleteTask.class);
            Bundle dataBundle = new Bundle();
            dataBundle.putString("previousActivity", getIntent().getStringExtra("CreateTask"));
            dataBundle.putString("from", getIntent().getStringExtra("from"));
            dataBundle.putString("tag", getIntent().getStringExtra("tag"));
            dataBundle.putString("desc", getIntent().getStringExtra("desc"));
            dataBundle.putInt("amount", getIntent().getIntExtra("amount",0));
            intent.putExtras(dataBundle);
            startActivity(intent);
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
}
