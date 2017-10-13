package main.taskmanager.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import main.taskmanager.R;

public class CreateUsers extends AppCompatActivity {
    public static String groupName;
    private ListView obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_users);
        TextView textView = (TextView) findViewById(R.id.txtViewShwMssg);
        groupName = getIntent().getStringExtra("groupName");
        textView.setText("Group Name : "  + groupName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.Add_User:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                dataBundle.putString("groupName", groupName);

                Intent intent = new Intent(getApplicationContext(),DisplayUser.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
