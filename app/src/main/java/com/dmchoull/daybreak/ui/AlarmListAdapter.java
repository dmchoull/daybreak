package com.dmchoull.daybreak.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.models.Alarm;

import java.util.List;

public class AlarmListAdapter extends ArrayAdapter<Alarm> {
    private final Context context;

    public AlarmListAdapter(Context context, int resource, List<Alarm> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.alarm_list_item, parent, false);
        }

        TextView textView = (TextView) itemView.findViewById(R.id.alarm_list_item_text);
        textView.setText(getItem(position).toString());

        return itemView;
    }
}
