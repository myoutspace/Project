package main.taskmanager.javaActions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import main.taskmanager.Display.HomePage;
import main.taskmanager.R;

/**
 * Created by Julien on 2017-11-12.
 */

public class GroupSelectionAdapter extends ArrayAdapter<String> {

    DatabaseHelper databaseHelper;
    Context activityContext;
    final int BACKGROUND_COLOR = ContextCompat.getColor(getContext(), R.color.colorLightGray);
    private static class ViewHolder {
        private Button groupButton;
    }

    public GroupSelectionAdapter(Context context, ArrayList<String> items, DatabaseHelper databaseHelper){
        super(context, R.layout.group_list_item,items);
        this.databaseHelper = databaseHelper;
        activityContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        GroupSelectionAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.group_list_item, parent, false);

            viewHolder = new GroupSelectionAdapter.ViewHolder();
            viewHolder.groupButton = (Button) convertView.findViewById(R.id.groupButton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupSelectionAdapter.ViewHolder) convertView.getTag();
        }

        final String groupName = getItem(position);

        if (groupName!= null) {
            viewHolder.groupButton.setText(groupName);
            if(position %2 == 0)
                viewHolder.groupButton.setBackgroundColor(BACKGROUND_COLOR);
            viewHolder.groupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHelper.setActiveGroup(groupName.toLowerCase());
                    Intent intent = new Intent(activityContext, HomePage.class);
                    activityContext.startActivity(intent);
                }
            });

            viewHolder.groupButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }

        return convertView;
    }

}
