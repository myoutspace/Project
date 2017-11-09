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

    private ArrayList<User> userList;
    Context context;

    public MainDrawerListAdapter(Context context, ArrayList<User> items) {
        super(context, R.layout.drawer_list_item, items);
        this.context = context;
        userList = items;
    }

    /*private view holder class*/
    private static class ViewHolder {
        static TextView pointAmount;
        static TextView userName;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.drawer_list_item,parent,false);

            ViewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.pointAmount = (TextView) convertView.findViewById(R.id.pointAmount);

            result = convertView;

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.userName.setText(user.getUsername());
        viewHolder.pointAmount.setText(user.getPointAmount());

        return convertView;

    }
}

