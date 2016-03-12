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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by stephanielin on 3/2/16.
 */
public class CustomFragment extends CardFragment {


    public static CustomFragment create(CharSequence title, CharSequence text, CharSequence term, CharSequence id) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();
        if(title != null) {
            args.putCharSequence("CardFragment_title", title);
        }

        if(text != null) {
            args.putCharSequence("CardFragment_text", text);
        }

        if(term != null) {
            args.putCharSequence("CardFragment_term", term);
        }

        if(id != null) {
            args.putCharSequence("CardFragment_id", id);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("T", "IN CUSTOM FRAGMENT");
        System.out.println(container);
        View card = inflater.inflate(R.layout.card, container, false);

        LinearLayout screen = (LinearLayout) card.findViewById(R.id.screen);

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
        }

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getActivity(), WatchToPhoneService.class);
                Bundle bundle = new Bundle();
                bundle.putString("NAME", args.getCharSequence("CardFragment_title").toString());
                bundle.putString("PARTY", args.getCharSequence("CardFragment_text").toString());
                bundle.putString("TERM", args.getCharSequence("CardFragment_term").toString());
                bundle.putString("ID", args.getCharSequence("CardFragment_id").toString());
                sendIntent.putExtras(bundle);
                getActivity().startService(sendIntent);
                Log.d("T", "DONE ");
            }

        });

        return card;
    }

}
