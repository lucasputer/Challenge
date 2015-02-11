package com.application.challenge.challenge.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.parse.ChallengeObject;
import com.application.challenge.challenge.domain.parse.PhotoObject;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListViewAdapter extends ParseQueryAdapter<ChallengeObject> {

    public ChallengesListViewAdapter(Context context){

        super(context,new ParseQueryAdapter.QueryFactory<ChallengeObject>(){
            public ParseQuery<ChallengeObject> create(){
                ParseQuery query = new ParseQuery("Challenge");
                query.include("firstPhoto");

                return query;
            }
        });


    }


    @Override
    public View getItemView(ChallengeObject challenge, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.element_challenge_row, null);
        }

        super.getItemView(challenge, v, parent);

        ParseImageView challengeImage = (ParseImageView) v.findViewById(R.id.circled_challenge_image);
        ParseFile photoFile = challenge.getFirstPhoto().getParseFile("thumbnail");
        if (photoFile != null) {
            challengeImage.setParseFile(photoFile);
            challengeImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.challenge_title);
        titleTextView.setText(challenge.getName());
        TextView subtitleTextView = (TextView) v.findViewById(R.id.challenge_subtitle);
        subtitleTextView.setText(challenge.getDescription());
        return v;
    }


}
