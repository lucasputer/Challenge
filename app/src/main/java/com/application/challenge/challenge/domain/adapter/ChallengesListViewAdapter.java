package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.codec.binary.StringUtils;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListViewAdapter extends ParseQueryAdapter<ChallengeObject> {

    public ChallengesListViewAdapter(Context context,ParseQueryAdapter.QueryFactory<ChallengeObject>  queryFactory){
        super(context,queryFactory);
    }


    @Override
    public View getItemView(ChallengeObject challenge, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.element_challenge_row, null);
        }

        super.getItemView(challenge, v, parent);

        ParseImageView challengeImage = (ParseImageView) v.findViewById(R.id.circled_challenge_image);
        if(challenge.getFirstPhoto() != null){
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
        }


        TextView titleTextView = (TextView) v.findViewById(R.id.challenge_title);
        titleTextView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        titleTextView.setText(challenge.getName());
        TextView subtitleTextView = (TextView) v.findViewById(R.id.challenge_subtitle);
        subtitleTextView.setText(challenge.getDescription());
        return v;
    }


}
