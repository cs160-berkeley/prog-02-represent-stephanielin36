package com.example.stephanielin.represent;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        String[][] names = {{"Barbara Boxer", "Barbara Lee", "Dianne Feinstein"}};
        String[][] parties = {{"Democrat", "Democrat", "Democrat"}};
        int[][] photos = {{R.drawable.barbaraboxer, R.drawable.barbaralee, R.drawable.diannefeinstein}};
        String[][] vote2012 = {{"Alameda County, CA", "Obama: 78% of Vote", "Romney: 22% of Vote"}};
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyGridPagerAdapter(this, names, parties, photos, vote2012, getFragmentManager()));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                Log.d("T", "ABOUT TO SEND TOAST");

                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(getApplicationContext(), "SHAKE DETECTED!", duration);
                toast.show();
                String[][] names = {{"Barbara Boxer", "Barbara Lee", "Dianne Feinstein"}};
                String[][] parties = {{"Democrat", "Democrat", "Democrat"}};
                int[][] photos = {{R.drawable.barbaraboxer, R.drawable.barbaralee, R.drawable.diannefeinstein}};
                String[][] vote2012 = {{"Los Angeles, CA", "Obama: 100% of Vote", "Romney: 0% of Vote"}};
                final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
                pager.setAdapter(new MyGridPagerAdapter(getApplicationContext(), names, parties, photos, vote2012, getFragmentManager()));

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
