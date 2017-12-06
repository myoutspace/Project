package main.taskmanager.Display;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.*;

import java.util.ArrayList;

import main.taskmanager.R;
import main.taskmanager.javaActions.*;

public class ResourceSelection extends AppCompatActivity {

    ArrayList<DataResource> dataResources;
    ListView listView;
    TextView message;
    ImageButton confirm;
    private ResourceListAdapter adapter;
    private DatabaseHelper database;
    AlertDialog.Builder dialog;
    ArrayList<String> resources;
    ArrayList<String> returnResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ressource_selection);
        database = DatabaseHelper.getInstance(getApplicationContext());
        listView = (ListView) findViewById(R.id.list_resources);
        message = (TextView) findViewById(R.id.txtViewNoResource);
        confirm = (ImageButton) findViewById(R.id.confirmResources);
        dialog = new AlertDialog.Builder(ResourceSelection.this);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Add a resource");
        dialog.setMessage("Resource name: ");
        returnResources = new ArrayList<>();
        resources = database.getAllResources();
        dataResources = new ArrayList<>();
        if (resources.isEmpty()) {
            listView.setVisibility(View.GONE);
            ((ImageButton) findViewById(R.id.cancelResources)).setVisibility(View.VISIBLE);
            message.setText("There are currently no ressources available!");
            message.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.GONE);
        } else {
            for (String r : resources) {
                dataResources.add(new DataResource(SimpleAction.capitalizeString(r), false));
            }
            adapter = new ResourceListAdapter(dataResources, getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
                        id) {
                    final Resource resource = new Resource((dataResources.get(position)).name
                            .toLowerCase(), database.getActiveGroup());
                    deleteDialog.setTitle("Are you sure you want to delete " + SimpleAction
                            .capitalizeString(resource.getName() +
                            "?"));
                    deleteDialog.setButton(deleteDialog.BUTTON_POSITIVE, "Yes", new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database.deleteResource(resource);
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    deleteDialog.setButton(deleteDialog.BUTTON_NEGATIVE, "No", new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteDialog.dismiss();
                        }
                    });
                    deleteDialog.show();
                    return true;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CheckBox cb = (CheckBox) view.findViewById(R.id.checkBoxResource);
                    if (!cb.isChecked()) {
                        cb.setChecked(true);
                        returnResources.add(dataResources.get(position).name);
                    } else {
                        cb.setChecked(false);
                        returnResources.remove(dataResources.get(position).name); // remove the
                        // position when the
                    }
                }
            });
        }
    }

    public void addResource(View view) {
        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(context);
        name.setHint("Name");
        layout.addView(name);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, @SuppressWarnings("unused") final int
                    which) {
                if (resources.contains(name.getText().toString().toLowerCase())) {
                    Toast.makeText(getApplicationContext(), "The resource exist already",
                            Toast.LENGTH_SHORT).show();
                } else if (name.getText().toString().trim().equalsIgnoreCase("")) {
                    name.setError("This field can not be blank");
                } else {
                    Resource resource = new Resource(name.getText().toString().toLowerCase(),
                            database.getActiveGroup());
                    database.addResource(resource);
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(layout);
        dialog.show();
    }

    public void onConfirmResources(View view) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("listResource", returnResources);
        intent.putExtra("previousActivity", "ResourceSelection");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelResources(View view) {
        Intent intent = new Intent();
        intent.putExtra("previousActivity", "ResourceSelection");
        setResult(RESULT_OK, intent);
        finish();
    }

}
