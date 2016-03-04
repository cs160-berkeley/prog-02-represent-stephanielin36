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

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath() != null) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String[] info = value.split(";");

            Context context = getApplicationContext();
            Intent intent = new Intent(this, DetailedView.class);
            Bundle extras = new Bundle();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            extras.putString("NAME", info[0]);
            extras.putString("PARTY", info[1]);
            //extras.putInt("IMAGE", Integer.parseInt(info[2]));
            intent.putExtras(extras);
            startActivity(intent);

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}

