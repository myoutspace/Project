package main.taskmanager.javaActions;

/**
 * Class ResourceListAdapter.
 * Used to create an adapter for the list view used for resources.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import main.taskmanager.R;

import java.util.ArrayList;

public class ResourceListAdapter extends ArrayAdapter {

    /**
     * dataSet ArrayList<DataResource> to represent the a resource name and a check box
     */

    private ArrayList<DataResource> dataSet;
    Context mContext;

    /**
     * viewHoder Class used to get a text view and checkbox view
     */
    private static class ViewHolder {

        /**
         * txtName TextView to represent the TextView
         * checkBox CheckBox to represent the CheckBox
         */

        TextView txtName;
        CheckBox checkBox;
    }


    /**
     * The constructor
     * @param data all the resources.
     * @param context Application context.
     */

    public ResourceListAdapter(ArrayList data, Context context) {
        super(context, R.layout.row_item_ressource, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public DataResource getItem(int position) {
        return dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .row_item_ressource, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtResource);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxResource);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        DataResource item = getItem(position);
        viewHolder.txtName.setText(item.name);
        viewHolder.checkBox.setClickable(false);

        return result;
    }
}
