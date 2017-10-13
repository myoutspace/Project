package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import main.taskmanager.R;

public class CreateGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public void onCreateGroup(View view){
        Intent intent = new Intent(this, GroupConfirmation.class);
        EditText group = (EditText) findViewById(R.id.edtTxtGrpName);
        String groupName = group.getText().toString();
        intent.putExtra("groupName", groupName);
        startActivity(intent);
    }
}
