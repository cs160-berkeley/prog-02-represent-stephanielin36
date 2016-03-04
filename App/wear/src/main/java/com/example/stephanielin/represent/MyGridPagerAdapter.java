package com.example.stephanielin.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.util.Log;

/**
 * Created by stephanielin on 3/2/16.
 */
public class MyGridPagerAdapter extends FragmentGridPagerAdapter {


    private final Context context;
    private final String[][] names;
    private final String[][] parties;
    private final int[][] images;
    private final String[][]  vote2012;

    public MyGridPagerAdapter(Context context, String[][] names, String[][] parties, int[][] imgs,
                              String[][] vote2012, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.names = names;
        this.parties = parties;
        this.images = imgs;
        this.vote2012 = vote2012;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        if (col == getColumnCount(row)-1) {
            String county = vote2012[0][0];
            String obama = vote2012[0][1];
            String romney = vote2012[0][2];

            Custom2012 fragment = Custom2012.create(county, obama, romney);
            Log.d("T", "GOT A CUSTOM2012 FRAGMENT");

            return fragment;
        } else {
            String name = names[row][col];
            String party = parties[row][col];
            int image = images[row][col];
            CustomFragment fragment = CustomFragment.create(name, party, image);

            return fragment;
        }
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {

        return names[i].length+1;
    }
}
