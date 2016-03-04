package com.example.stephanielin.represent;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by stephanielin on 2/27/16.
 */
public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks{

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();
    final Service __this = this;
    private String name;
    private String party;
    private int image;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("T", "IN WATCH TO PHONE ON CREATE");
        //initialize the googleAPIClient for message passing
        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .build();


        //and actually connect it
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static Asset createAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }

    public int onStartCommand(Intent intent, int flags, int startID) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            name = bundle.getString("NAME");
            party = bundle.getString("PARTY");
            image = bundle.getInt("IMAGE");
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
//            Asset asset = createAssetFromBitmap(bitmap);
//            PutDataRequest request = PutDataRequest.create("/image");
//            request.putAsset("profileImage", asset);
//            Wearable.DataApi.putDataItem(mWatchApiClient, request);
        }
        mWatchApiClient.connect();
        Log.d("T", "IN WATCH TO PHONE CONNECTED");
        return START_STICKY;
    }

    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
    public void onConnected(Bundle bundle) {
        Log.d("T", "in onconnected");
        //final String name = bundle.getString("NAME");
        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        nodes = getConnectedNodesResult.getNodes();
                        Log.d("T", "found nodes");
                        //when we find a connected node, we populate the list declared above
                        //finally, we can send a message
                        sendMessage("/"+name, name + ";" + party + ";" + image);
                        Log.d("T", "sent");
                        __this.stopSelf();
                    }
                });

    }

    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
    public void onConnectionSuspended(int i) {}

    private void sendMessage(final String path, final String text ) {
        for (Node node : nodes) {
            Wearable.MessageApi.sendMessage(
                    mWatchApiClient, node.getId(), path, text.getBytes());
        }
    }
}
