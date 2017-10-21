package main.taskmanager.Display;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import main.taskmanager.R;

public class HomePage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter mStringAdaptor;
    private String[] mStringOfPlanets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mStringOfPlanets = this.getResources().getStringArray(R.array.planets_array);
        mStringAdaptor = new ArrayAdapter<>(this, R.layout.drawer_list_item, mStringOfPlanets);

        // Set the adapter for the list view
        mDrawerList.setAdapter(mStringAdaptor);
    }
}
