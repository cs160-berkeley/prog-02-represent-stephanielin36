package com.example.stephanielin.represent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by stephanielin on 2/27/16.
 */
public class WatchListenerService extends WearableListenerService {

    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages

    private String names;
    private String parties;
    private String terms;
    private String ids;
    private String vote;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        if (messageEvent.getPath().equalsIgnoreCase("/names")) {
            names = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/parties")) {
            parties = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/terms")) {
            terms = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/ids")) {
            ids = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/2012")) {
            vote = value;
        } else if (messageEvent.getPath().equalsIgnoreCase("/start_intent")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle extras = new Bundle();
            extras.putString("NAMES", names);
            extras.putString("PARTIES", parties);
            extras.putString("TERMS", terms);
            extras.putString("IDS", ids);
            extras.putString("2012", vote);
            intent.putExtras(extras);
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
