package com.example.stephanielin.represent;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by stephanielin on 3/10/16.
 */
public class RandomZip {

    private String zip;
    private Context context;
    private ArrayList<String> zipcodes;

    public RandomZip(Context context) {
        this.context = context;
        zipcodes = new ArrayList<>();
    }

    public String findRandom() {
        Log.d("T", "in RANDOM");
        InputStream fstream = null;
        try {
            fstream = context.getAssets().open("postal-codes.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) != null)   {
                zipcodes.add(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(zipcodes);
        Random random = new Random();
        int num = random.nextInt(zipcodes.size());
        //return zipcodes.get(num);
        return "98052";
    }

}
