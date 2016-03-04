package com.example.stephanielin.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by stephanielin on 3/1/16.
 */

public class CongressionalAdapter extends BaseAdapter  {

    private final Context context;
    private final String[] names;
    private final String[] parties;
    private final String[] emails;
    private final String[] websites;
    private final String[] tweets;
    private final int[] photos;

    public CongressionalAdapter(Context context, String[] names, String[] parties, String[] emails,
                                String[] websites, String[] tweets, int[] photos) {

        this.context = context;
        this.names = names;
        this.parties = parties;
        this.emails = emails;
        this.websites = websites;
        this.tweets = tweets;
        this.photos = photos;
    }

    public class Holder {
        TextView name;
        TextView party;
        TextView email;
        TextView website;
        TextView tweet;
        ImageView picView;
        Button moreInfo;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Holder holder = new Holder();
        View row = inflater.inflate(R.layout.congressional_list_view, null);
        holder.picView = (ImageView) row.findViewById(R.id.congressional_img);
        holder.name = (TextView) row.findViewById(R.id.congressional_name);
        holder.party = (TextView) row.findViewById(R.id.congressional_party);
        holder.email = (TextView) row.findViewById(R.id.congressional_email);
        holder.website = (TextView) row.findViewById(R.id.congressional_website);
        holder.tweet = (TextView) row.findViewById(R.id.congressional_tweet);
        holder.moreInfo = (Button) row.findViewById(R.id.more_info);

        holder.picView.setImageResource(photos[position]);
        holder.name.setText(names[position]);
        holder.party.setText(parties[position]);
        if (parties[position].equals("Democrat")) {
            holder.party.setTextColor(Color.parseColor("#0431B4"));
        } else {
            holder.party.setTextColor(Color.parseColor("#DF0101"));
        }

        holder.email.setText(emails[position]);
        holder.tweet.setText(tweets[position]);

        holder.email.setClickable(true);
        holder.email.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='mailto:" + emails[position] + "'> Email </a>";
        holder.email.setText(Html.fromHtml(text));

        holder.website.setClickable(true);
        holder.website.setMovementMethod(LinkMovementMethod.getInstance());
        String web = "<a href='" + websites[position] + "'> Website </a>";
        holder.website.setText(Html.fromHtml(web));

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(context, DetailedView.class);
                Bundle extras = new Bundle();
                extras.putString("NAME", names[position]);
                extras.putString("PARTY", parties[position]);
                extras.putInt("IMAGE", photos[position]);
//                holder.picView.buildDrawingCache();
//                Bitmap image = holder.picView.getDrawingCache();
//                extras.putParcelable("image", image);
                sendIntent.putExtras(extras);
                context.startActivity(sendIntent);
            }
        });

//        holder.email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(holder.email.getText().toString()));
//                context.startActivity(intent);
//            }
//        });

        return row;
    }

    @Override
    public int getCount() {
        return names.length;
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
