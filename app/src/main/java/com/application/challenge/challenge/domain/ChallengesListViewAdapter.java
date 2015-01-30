package com.application.challenge.challenge.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListViewAdapter extends ArrayAdapter<ChallengesItem> {

    private final Context context;
    private ArrayList<ChallengesItem> challengesList;

    public ChallengesListViewAdapter(Context context, ArrayList<ChallengesItem> challengesList){
        super(context, R.layout.element_challenge_row,challengesList);
        this.context = context;
        this.challengesList = new ArrayList<ChallengesItem>();
        this.challengesList.addAll(challengesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.element_challenge_row, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.challenge_title);
        TextView subtitle = (TextView) rowView.findViewById(R.id.challenge_subtitle);
        CircularImageView imageView = (CircularImageView) rowView.findViewById(R.id.circled_challenge_image);

        imageView.setBorderWidth(0);

        title.setText(challengesList.get(position).getTitle());
        subtitle.setText(challengesList.get(position).getSubtitle());

        Picasso.with(context) //
                .load(challengesList.get(position).getUrl()) //
                .fit() //
                .tag(context) //
                .into(imageView);

        return rowView;
    }


}
