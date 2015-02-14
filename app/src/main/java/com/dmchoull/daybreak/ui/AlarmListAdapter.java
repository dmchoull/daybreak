package com.dmchoull.daybreak.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    static class ViewHolder {
        TextView text;
        Button deleteButton;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder viewHolder;

        if (convertView == null) {
            itemView = createView(parent);
            viewHolder = buildViewHolder(itemView);
            itemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        updateView(position, viewHolder);

        return itemView;
    }

    private View createView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.alarm_list_item, parent, false);
    }

    private ViewHolder buildViewHolder(View itemView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) itemView.findViewById(R.id.alarm_list_item_text);
        viewHolder.deleteButton = (Button) itemView.findViewById(R.id.delete_alarm_button);
        return viewHolder;
    }

    private void updateView(int position, ViewHolder viewHolder) {
        viewHolder.text.setText(getItem(position).toString());
        viewHolder.deleteButton.setTag(getItem(position));
    }

    @Override public long getItemId(int position) {
        return getItem(position).getId();
    }
}
