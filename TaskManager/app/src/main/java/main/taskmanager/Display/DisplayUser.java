package main.taskmanager.Display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import main.taskmanager.R;
import main.taskmanager.database.UserInfoDB;
import main.taskmanager.javaActions.User;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayUser extends Activity {
    int from_Where_I_Am_Coming = 0;
    private UserInfoDB mydb ;

    TextView name ;
    TextView title;
    TextView pass;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        name = (TextView) findViewById(R.id.editTextName);
        title = (TextView) findViewById(R.id.editTextTitle);
        pass = (TextView) findViewById(R.id.editTextPass);

        mydb = new UserInfoDB(this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            int value = extras.getInt("id");

            if(value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getAllUsers(value);
                id_To_Update = value;
                rs.moveToFirst();

                String inName = rs.getString(rs.getColumnIndex("name"));
                String password = rs.getString(rs.getColumnIndex("password"));
                String inTitle = rs.getString(rs.getColumnIndex("title"));
                String points = rs.getString(rs.getColumnIndex("points"));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)inName);
                name.setFocusable(false);
                name.setClickable(false);

                title.setText((CharSequence)inTitle);
                title.setFocusable(false);
                title.setClickable(false);

                pass.setText((CharSequence)password);
                pass.setFocusable(false);
                pass.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.create_users, menu);
            } else{
                getMenuInflater().inflate(R.menu.create_users, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Add_User:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                title.setEnabled(true);
                title.setFocusableInTouchMode(true);
                title.setClickable(true);

                pass.setEnabled(true);
                pass.setFocusableInTouchMode(true);
                pass.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_User)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteUser(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(true){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),CreateUsers.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                User user = new User(name.getText().toString(), "200", pass.getText().toString(), title.getText().toString(), extras.getString("groupName"));
                if(mydb.addUser(user)){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Bundle dataBundle = new Bundle();
                dataBundle.putString("groupName", extras.getString("groupName"));
                Intent intent = new Intent(getApplicationContext(),CreateUsers.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        }
    }
}
