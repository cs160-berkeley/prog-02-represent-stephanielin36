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

public class MainActivity extends Activity {

    private TextView mTextView;
    private Button mFeedBtn;

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Log.d("T", "in MainActivity, extras: " + extras);

        if (extras != null) {

            Log.d("T", "in MainActivity starting intent");
            Intent newIntent = new Intent(getBaseContext(), CongressionalView.class);
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
