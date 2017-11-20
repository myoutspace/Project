package main.taskmanager.Display;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import main.taskmanager.javaActions.Task;
import main.taskmanager.javaActions.User;

public class CompleteTask extends AppCompatActivity {

    private static String postedBy;
    private static int points;
    private static String tag;
    private static Spinner completedBy;
    private static String description;
    private static String groupName;
    private static DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_task);
        database = DatabaseHelper.getInstance(getApplicationContext());
        groupName = database.getActiveGroup();
        ArrayList<User> users = database.getAllActiveUsers();
        String[] usersArray = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {
            usersArray[i] = users.get(i).getUsername();
        }

        completedBy = (Spinner) findViewById(R.id.spinnerCompleteTask);
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout
                .simple_spinner_dropdown_item, usersArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        completedBy.setAdapter(adapter);

        postedBy = getIntent().getStringExtra("from");
        points = getIntent().getIntExtra("amount", 0);
        tag = getIntent().getStringExtra("tag");
        description = getIntent().getStringExtra("desc");
        TextView poster = (TextView) findViewById(R.id.postingUser);
        TextView amount = (TextView) findViewById(R.id.pointAmount);
        TextView theTag = (TextView) findViewById(R.id.tag);
        TextView theDescription = (TextView) findViewById(R.id.taskDescription);
        poster.setText(postedBy);
        amount.setText(Integer.toString(points));
        theTag.setText(tag);
        theDescription.setText(description);
    }

    public void onCompleteTask(View view){
        User userPost = database.getUser((String) completedBy.getSelectedItem());
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_enter_group_name, null);
        dialog.setView(dialogView);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editTxtPass);
        dialog.setTitle("Delete group");
        dialog.setMessage("Group name");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user;
                user = new User(editText.getText().toString().toLowerCase(), 0, "", "", database
                        .getActiveGroup());
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

        Task task = new Task(postedBy, points, tag, description);
        database.deleteTask(task, database.getActiveGroup());
        Toast.makeText(getApplicationContext(), "Deleted Successfully",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
