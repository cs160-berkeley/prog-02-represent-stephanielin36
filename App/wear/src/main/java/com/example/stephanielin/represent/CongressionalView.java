package com.example.stephanielin.represent;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.widget.Toast;

public class CongressionalView extends Activity {

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private String[][] names = {{}};
    private String[][] parties = {{}};
    private String[][] terms = {{}};
    private String[][] ids = {{}};
    private String[][] vote = {{}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        names[0] = extras.getString("NAMES").split(";");
        parties[0] = extras.getString("PARTIES").split(";");
        terms[0] = extras.getString("TERMS").split(";");
        ids[0] = extras.getString("IDS").split(";");
        vote[0] = extras.getString("2012").split(";");
        Log.d("T", "congressional view: " + extras.getString("2012"));


        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

        pager.setAdapter(new MyGridPagerAdapter(this, names, parties, terms, ids, vote, getFragmentManager()));

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
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                Bundle bundle = new Bundle();
                bundle.putString("ZIP", zip);
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
