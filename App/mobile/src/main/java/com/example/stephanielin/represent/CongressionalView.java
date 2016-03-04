package com.example.stephanielin.represent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class CongressionalView extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView congressionalList = (ListView) findViewById(R.id.congressional_list_view);

        String[] names = {"Senator Barbara Boxer", "Representative Barbara Lee", "Senator Dianne Feinstein"};
        String[] parties = {"Democrat", "Democrat", "Democrat"};
        String[] emails = {"senator@boxer.senate.gov", "barbaralee@lee.house.gov", "senator@feinstein.senate.gov"};
        String[] websites = {"http://www.boxer.senate.gov/", "http://www.lee.house.gov", "http://www.feinstein.senate.gov"};
        String[] tweets = {"Putting the country first means Obama nominating a Justice and the Senate doing its constitutional duty by voting on the nominee.",
                "Today we reflect on the contributions and leadership of U.S. presidents throughout our nation’s great history. Happy #PresidentsDay.",
                "Today we reflect on the contributions and leadership of U.S. presidents throughout our nation’s great history. Happy #PresidentsDay."};
        int[] photos = {R.drawable.barbaraboxer, R.drawable.barbaralee, R.drawable.diannefeinstein};

        final CongressionalAdapter adapter = new CongressionalAdapter(this, names, parties, emails, websites, tweets, photos);
        congressionalList.setAdapter(adapter);

//        btn = (Button) findViewById(R.id.btn);
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            String text = extras.getString("CAT_NAME");
//            btn.setText(text);
//        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
