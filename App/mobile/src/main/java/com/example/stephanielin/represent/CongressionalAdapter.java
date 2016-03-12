package com.example.stephanielin.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * Created by stephanielin on 3/1/16.
 */

public class CongressionalAdapter extends BaseAdapter  {

    private static final String TWITTER_KEY = "c4152cca2b28d73cc68705d4b75fa015c01d390b";
    private static final String TWITTER_SECRET = "a63af82a8adad8ee9148bfc6292d2a93b5f2760e9306041a92e381bf57b733ea";

    private Context context;
    private final String[] names;
    private final String[] parties;
    private final String[] emails;
    private final String[] websites;
    private final String[] twitter_ids;
    private final Bitmap[] photos;
    private final String[] ids;
    private String bills = "";
    private String committees = "";
    private String[] terms;

    public CongressionalAdapter(Context context, String[] names, String[] parties, String[] emails,
                                String[] websites, String[] twitter_ids, Bitmap[] photos, String[] ids, String[] terms) {

        this.context = context;
        this.names = names;
        this.parties = parties;
        this.emails = emails;
        this.websites = websites;
        this.twitter_ids = twitter_ids;
        this.photos = photos;
        this.ids = ids;
        this.terms = terms;
    }

    public class Holder {
        TextView name;
        TextView party;
        TextView email;
        TextView website;
        ImageView picView;
        Button moreInfo;
        LinearLayout tweet;
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
        holder.tweet = (LinearLayout) row.findViewById(R.id.tweet);
        holder.moreInfo = (Button) row.findViewById(R.id.more_info);

        holder.picView.setImageBitmap(photos[position]);
        holder.name.setText(names[position]);
        holder.party.setText(parties[position]);
        if (parties[position].equals("Democrat")) {
            holder.party.setTextColor(Color.parseColor("#0431B4"));
        } else if (parties[position].equals("Republican")){
            holder.party.setTextColor(Color.parseColor("#DF0101"));
        } else {
            holder.party.setTextColor(Color.parseColor("#2E2E2E"));
        }

        holder.email.setText(emails[position]);

        holder.email.setClickable(true);
        holder.email.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='mailto:" + emails[position] + "'> Email </a>";
        holder.email.setText(Html.fromHtml(text));

        holder.website.setClickable(true);
        holder.website.setMovementMethod(LinkMovementMethod.getInstance());
        String web = "<a href='" + websites[position] + "'> Website </a>";
        holder.website.setText(Html.fromHtml(web));

//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(context, new Twitter(authConfig));

//        //StatusesService statusesService = twitterApiClient.getStatusesService();
////
////        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
////            @Override
////            public void success(Result<AppSession> appSessionResult) {
////                AppSession session = appSessionResult.data;
//                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
//                twitterApiClient.getStatusesService().userTimeline(null, twitter_ids[position], null, (long) 1, null,
//                        null, null, null, null, new Callback<List<Tweet>>() {
//                            @Override
//                            public void success(Result<List<Tweet>> result) {
//                                Tweet t = result.data.get(0);
//                                TweetView tweetView = new TweetView(context, t);
//                                holder.tweet.addView(tweetView);
//                            }
//
//                            @Override
//                            public void failure(TwitterException exception) {
//                                Log.d("TwitterKit", "Load Tweet failure", exception);
//                            }
//                        });
//            //}
//
////            @Override
////            public void failure(TwitterException e) {
////                TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
////                Fabric.with(context, new Twitter(authConfig));
////
////                Log.d("T", "FAILURE YOU SUCK");
////                e.printStackTrace();
////            }
////        });
////        twitterApiClient.getStatusesService().userTimeline(null, twitter_ids[position], null, (long) 1, null,
////                null, null, null, null, new Callback<List<Tweet>>() {
////                    @Override
////                    public void success(Result<List<Tweet>> result) {
////                        Tweet t = result.data.get(0);
////                        TweetView tweetView = new TweetView(context, t);
////                        holder.tweet.addView(tweetView);
////                    }
////
////                    @Override
////                    public void failure(TwitterException exception) {
////                        Log.d("TwitterKit", "Load Tweet failure", exception);
////                    }
////                });
////        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
////            @Override
////            public void success(Result<Tweet> result) {
////                TweetView tweetView = new TweetView(context, result.data);
////                parentView.addView(tweetView);
////            }
////            @Override
////            public void failure(TwitterException exception) {
////                Log.d("TwitterKit", "Load Tweet failure", exception);
////            }
////        });
//

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedView.class);
                Bundle extras = new Bundle();
                bills = "";
                committees = "";
                extras.putString("NAME", names[position]);
                extras.putString("PARTY", parties[position]);
                extras.putString("TERM", terms[position]);
                extras.putString("ID", ids[position]);

                Bitmap img = photos[position];
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                extras.putByteArray("IMAGE", byteArray);


                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });

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
