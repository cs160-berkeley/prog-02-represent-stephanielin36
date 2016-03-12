package com.example.stephanielin.represent;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by stephanielin on 3/1/16.
 */
public class BulletListAdapter extends BaseAdapter {

    private Context context;
    private String[] items;

    public BulletListAdapter(Context context, String[] items) {
        this.context = context;
        this.items = items;
    }

    public class Holder {
        TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final Holder holder = new Holder();
        View row = inflater.inflate(R.layout.bullet_list, null);
        holder.text = (TextView) row.findViewById(R.id.list_item);
        holder.text.setText(items[position]);
        holder.text.setTextColor(Color.parseColor("#2E2E2E"));
        holder.text.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        return row;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
