package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import main.taskmanager.R;
import main.taskmanager.javaActions.DatabaseHelper;
import main.taskmanager.javaActions.Task;

public class CompleteTask extends AppCompatActivity {

    private static String postedBy;
    private static int points;
    private static String tag;
    private static String description;
    private static DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_task);
        database = DatabaseHelper.getInstance(getApplicationContext());
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
        Task task = new Task(postedBy, points, tag, description);
        database.deleteTask(task, database.getActiveGroup());
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
