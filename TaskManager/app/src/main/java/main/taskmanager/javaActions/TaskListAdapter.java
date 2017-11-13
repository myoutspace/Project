package main.taskmanager.javaActions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import main.taskmanager.R;

/**
 * Created by Julien on 2017-11-13.
 */

public class TaskListAdapter extends ArrayAdapter<Task> {

    private static class ViewHolder {
        private TextView taskUser;
        private TextView taskPoint;
        private TextView taskDescription;
    }

    public TaskListAdapter(Context context, ArrayList<Task> items) {
        super(context, R.layout.task_list_item, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskListAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.task_list_item, parent, false);

            viewHolder = new TaskListAdapter.ViewHolder();
            viewHolder.taskUser = (TextView) convertView.findViewById(R.id.postingUser);
            viewHolder.taskPoint = (TextView) convertView.findViewById(R.id.pointAmount);
            viewHolder.taskDescription = (TextView) convertView.findViewById(R.id.taskDescription);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TaskListAdapter.ViewHolder) convertView.getTag();
        }

        Task task = getItem(position);
        if (task!= null) {
            viewHolder.taskUser.setText(task.getUserPost());
            viewHolder.taskPoint.setText(String.valueOf(task.getPointAmount()));
            viewHolder.taskDescription.setText(task.getDescription());
        }

        return convertView;
    }
}
