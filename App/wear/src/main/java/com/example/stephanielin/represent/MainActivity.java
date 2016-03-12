package com.example.stephanielin.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Random;

public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "JPEDqvN8XUs4DRuWN3WtwznDC";
    private static final String TWITTER_SECRET = "hdJtDKdNUw20Xq1CEKsSWh54KJrkihlxriDVCWQy7KNDWQwX0D";


    private TextView mTextView;
    private Button mFeedBtn;

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Log.d("T", "in MainActivity, extras: " + extras);

        if (extras != null) {
            String names = extras.getString("NAMES");
            String parties = extras.getString("PARTIES");
            String terms = extras.getString("TERMS");
            String ids = extras.getString("IDS");
            String vote = extras.getString("2012");

            Intent newIntent = new Intent(getBaseContext(), CongressionalView.class);
            Bundle bundle = new Bundle();
            bundle.putString("NAMES", names);
            bundle.putString("PARTIES", parties);
            bundle.putString("TERMS", terms);
            bundle.putString("IDS", ids);
            bundle.putString("2012", vote);

            Log.d("T", vote);
            newIntent.putExtras(bundle);
            startActivity(newIntent);
        } else {
            setContentView(R.layout.activity_main);
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                Log.d("T", "ABOUT TO SEND TOAST");

                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(getApplicationContext(), "SHAKE DETECTED!", duration);
                toast.show();
                RandomZip r = new RandomZip(getApplicationContext());
                String zip = r.findRandom();
                Log.d("T", zip);
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                Bundle bundle = new Bundle();
                bundle.putString("ZIP", zip);
                Log.d("T", "SHAKE LISTENER ZIP: " +zip);
                sendIntent.putExtras(bundle);
                getBaseContext().startService(sendIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
