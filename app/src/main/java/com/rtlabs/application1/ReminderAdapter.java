package com.rtlabs.application1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReminderAdapter extends ArrayAdapter<Reminder> {

    public ReminderAdapter(Activity context, ArrayList<Reminder> androidFlavors) {
        super(context, 0, androidFlavors);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Reminder currentReminder = getItem(position);

        TextView descriptionTextView = listItemView.findViewById(R.id.description);
        descriptionTextView.setText(currentReminder.getDescription());

        TextView timeTextView = listItemView.findViewById(R.id.time);
        timeTextView.setText("Time : " + currentReminder.getTime());

        TextView dateTextView = listItemView.findViewById(R.id.date);
        dateTextView.setText("Date : " +currentReminder.getDate());

        return listItemView;
    }

}
