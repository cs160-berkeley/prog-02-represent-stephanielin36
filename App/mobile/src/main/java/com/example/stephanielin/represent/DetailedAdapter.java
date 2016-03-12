package com.example.stephanielin.represent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by stephanielin on 3/1/16.
 */
public class DetailedAdapter extends BaseAdapter {

    private final Context context;
    private final Bitmap img;
    private final String party;
    private final String term;
    private final String[] committees;
    private final String[] bills;

    public DetailedAdapter(Context context, Bitmap img, String party, String term, String[] committees, String[] bills) {
        this.img = img;
        this.context = context;
        this.party = party;
        this.term = term;
        this.committees = committees;
        this.bills = bills;
    }

    public class Holder {
        TextView party;
        TextView term;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final Holder holder = new Holder();
        View row = inflater.inflate(R.layout.detailed_list_view, null);

        final LinearLayout committeeList = (LinearLayout) row.findViewById(R.id.committee_list);
        final LinearLayout billsList = (LinearLayout) row.findViewById(R.id.bills_list);

        final BulletListAdapter committeeAdapter = new BulletListAdapter(context, committees);
        for (int i = 0; i < committeeAdapter.getCount(); i++) {
            View view = committeeAdapter.getView(i, null, committeeList);
            committeeList.addView(view);
        }
        final BulletListAdapter billsAdapter = new BulletListAdapter(context, bills);
        for (int i = 0; i < billsAdapter.getCount(); i++) {
            View view = billsAdapter.getView(i, null, billsList);
            billsList.addView(view);
        }

        holder.image = (ImageView) row.findViewById(R.id.detailed_img);
        holder.party = (TextView) row.findViewById(R.id.detailed_party);
        holder.term = (TextView) row.findViewById(R.id.detailed_term);

        holder.image.setImageBitmap(img);
        holder.party.setText(party);

        if (party.equals("Democrat")) {
            holder.party.setTextColor(Color.parseColor("#0431B4"));
        } else {
            holder.party.setTextColor(Color.parseColor("#DF0101"));
        }

        String end = "End of Term: " + term;
        holder.term.setText(end);

        return row;
    }

    @Override
    public int getCount() {
        return 1;
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
