package main.taskmanager.javaActions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import main.taskmanager.R;

/**
 * Created by Julien on 2017-11-07.
 */

public class MainDrawerListAdapter extends ArrayAdapter<User> {
    

    public MainDrawerListAdapter(Context context, ArrayList<User> items) {
        super(context, R.layout.drawer_list_item, items);
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater daInflater = LayoutInflater.from(getContext());
        View customView = daInflater.inflate(R.layout.drawer_list_item,parent,false);
        User user = getItem(position);

        String userName = user.getUsername();
        String pointAmount = String.valueOf(user.getPointAmount());

        TextView displayName = (TextView) customView.findViewById(R.id.userName);
        TextView displayPoints = (TextView) customView.findViewById(R.id.pointAmount);

        displayName.setText(userName);
        displayPoints.setText(pointAmount);


        return customView;

    }
}

