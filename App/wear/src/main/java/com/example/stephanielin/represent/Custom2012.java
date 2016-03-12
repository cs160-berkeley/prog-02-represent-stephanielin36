package com.example.stephanielin.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by stephanielin on 3/3/16.
 */
public class Custom2012 extends CardFragment {

    public static Custom2012 create(String county, String obama, String romney) {
        Custom2012 fragment = new Custom2012();
        Bundle args = new Bundle();
        if(county != null) {
            args.putString("CardFragment_county", county);
        }

        if(obama != null) {
            args.putString("CardFragment_obama", obama);
        }

        if(romney != null) {
            args.putString("CardFragment_romney", romney);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View card = inflater.inflate(R.layout.vote_2012, container, false);

        Log.d("T", "INFLATED LAYOUT CUSTOM 2012");
        final Bundle args = this.getArguments();
        if(args != null) {

            if(args.containsKey("CardFragment_county")) {
                TextView county = (TextView) card.findViewById(R.id.county);
                String c = args.getCharSequence("CardFragment_county").toString();
                county.setText(c);
            }

            if(args.containsKey("CardFragment_obama")) {
                TextView obama = (TextView) card.findViewById(R.id.obama_results);
                if(obama!= null) {
                    obama.setText(args.getCharSequence("CardFragment_obama"));
                }
            }

            if(args.containsKey("CardFragment_romney")) {
                TextView romney = (TextView) card.findViewById(R.id.romney_results);
                romney.setText(args.getCharSequence("CardFragment_romney"));
            }
        }
        return card;
    }
}
