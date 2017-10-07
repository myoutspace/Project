package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import main.taskmanager.R;

public class CreateGroup extends AppCompatActivity {
    public static final String GROUP_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public void onCreateGroup(View view){
        Intent intent = new Intent(this, GroupConfirmation.class);
        EditText group = (EditText) findViewById(R.id.edtTxtGrpName);
        String groupeName = group.getText().toString();
        intent.putExtra(GROUP_NAME, groupeName);
        startActivity(intent);
    }
}
