package com.example.stephanielin.represent;

/**
 * Created by stephanielin on 2/27/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String TOAST = "/send_toast";
    private String name;
    private String party;
    private String term;
    private String id;
    private String zip;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        if (messageEvent.getPath().equalsIgnoreCase("/zip")) {
            zip = value;
            Intent intent = new Intent(this, MainActivity.class);
            Bundle extras = new Bundle();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            extras.putString("ZIP", zip);
            Log.d("T", "PHONE LISTENER: " + zip);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase("/name")) {
            name = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/party")) {
            party = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/term")) {
            term = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/id")) {
            id = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/start_intent")) {

            Intent intent = new Intent(this, DetailedView.class);
            Bundle extras = new Bundle();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            extras.putString("NAME", name);
            extras.putString("PARTY", party);
            extras.putString("TERM", term);
            extras.putString("ID", id);
            intent.putExtras(extras);
            startActivity(intent);

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}

