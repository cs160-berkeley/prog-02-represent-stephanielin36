package com.example.stephanielin.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DetailedView extends AppCompatActivity {

    private String name;
    private String party;
    private String term;
    private int img;
    private String[] committees;
    private String[] bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        committees = new String[]{"Committee on Appropriations", "Committee on the Judiciary",
                "Committee on the Rules and Administration", "Select Committee on Intelligence"};
        bills = new String[]{"Dec.18.2015: S. 2372: Requiring Reporting of Online Terrorist Activity Act",
                "Dec.1.2015: S. 2337: Visa Waiver Program Security Enhancement Act"};
        term = "End of Term: 2018";

        final ListView detailedList = (ListView) findViewById(R.id.detailed_list_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Log.d("T", "got intent");
            name = extras.getString("NAME");
            Log.d("T", name);
            party = extras.getString("PARTY");
            if (name.contains("Barbara Boxer")) {
                Log.d("T", "barbs");
                img = R.drawable.barbaraboxer;
            } else if (name.contains("Barbara Lee")) {
                img = R.drawable.barbaralee;
                Log.d("T", "barbs");
            } if (name.contains("Dianne Feinstein")) {
                img = R.drawable.diannefeinstein;
                Log.d("T", "barbs");
            }

            setTitle(name);
        }

        final DetailedAdapter adapter = new DetailedAdapter(this, img, party, term, committees, bills);
        detailedList.setAdapter(adapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
