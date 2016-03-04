package com.example.stephanielin.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.wearable.view.CardFragment;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by stephanielin on 3/2/16.
 */
public class CustomFragment extends CardFragment {


    public static CustomFragment create(CharSequence title, CharSequence text, int image) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();
        if(title != null) {
            args.putCharSequence("CardFragment_title", title);
        }

        if(text != null) {
            args.putCharSequence("CardFragment_text", text);
        }

        if(image != 0) {
            args.putInt("CardFragment_image", image);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("T", "IN CUSTOM FRAGMENT");
        View card = inflater.inflate(R.layout.card, container, false);

        ImageView img = (ImageView) card.findViewById(R.id.img);

        Log.d("T", "INFLATED LAYOUT");
        final Bundle args = this.getArguments();
        if(args != null) {
            TextView name = (TextView) card.findViewById(R.id.name);
            if(args.containsKey("CardFragment_title") && name != null) {
                String person = args.getCharSequence("CardFragment_title").toString();
                name.setText(person);
            }

            if(args.containsKey("CardFragment_text")) {
                TextView party = (TextView) card.findViewById(R.id.party);
                if(party != null) {
                    party.setText(args.getCharSequence("CardFragment_text"));
                }
            }

            if(args.containsKey("CardFragment_image") && name != null) {
                img.setImageResource(args.getInt("CardFragment_image"));
            }
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getActivity(), WatchToPhoneService.class);
                Bundle bundle = new Bundle();
                bundle.putString("NAME", args.getCharSequence("CardFragment_title").toString());
                bundle.putString("PARTY", args.getCharSequence("CardFragment_text").toString());
                bundle.putInt("IMAGE", args.getInt("CardFragment_image"));
                sendIntent.putExtras(bundle);
                Log.d("T", "STARTING WATCH TO PHONE");
                getActivity().startService(sendIntent);
                Log.d("T", "DONE ");
            }

        });

        return card;
    }

}
