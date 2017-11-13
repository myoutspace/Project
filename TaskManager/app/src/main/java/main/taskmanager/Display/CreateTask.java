package main.taskmanager.Display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.User;

import static android.widget.ArrayAdapter.createFromResource;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        ArrayList<User> users = DatabaseHelper.getInstance(getApplicationContext()).getAllActiveUsers();
        String[] usersArray = new String[users.size()];
        for(int i=0; i<users.size(); i++)
        {
             usersArray[i] = users.get(i).getUsername();
        }


       Spinner spinner = (Spinner) findViewById(R.id.spinner);
//Create an ArrayAdapter using the string array and a default spinner layout
       ArrayAdapter<String> adapter;
       adapter = new ArrayAdapter<String>(getApplicationContext() ,android.R.layout.simple_spinner_dropdown_item, usersArray);
// Specify the layout to use when the list of choices appears
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
     spinner.setAdapter(adapter);




    }
}
