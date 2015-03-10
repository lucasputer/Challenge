package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by lucas on 16/1/15.
 */
public class UserSearchListViewAdapter extends ParseQueryAdapter<ParseUser> {

    public UserSearchListViewAdapter(Context context, QueryFactory<ParseUser> queryFactory){
        super(context,queryFactory);
    }


    @Override
    public View getItemView(final ParseUser user, View v, ViewGroup parent) {

        //TODO: onclick

        if (v == null) {
            v = View.inflate(getContext(), R.layout.element_user_row, null);
        }

        super.getItemView(user, v, parent);

        ParseImageView profilePicture = (ParseImageView) v.findViewById(R.id.circled_picture_profile_image);
        if(user.get("displayPictureThumbnail") != null){
            ParseFile photoFile = user.getParseFile("displayPictureThumbnail");
            if (photoFile != null) {
                profilePicture.setParseFile(photoFile);
                profilePicture.loadInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        // nothing to do
                    }
                });
            }
        }


        TextView username = (TextView) v.findViewById(R.id.user_username);
        username.setText(user.get("displayName").toString());

        TextView firstAndLastName = (TextView) v.findViewById(R.id.user_first_and_last_name);

            String name = "";
            if(user.get("firstName") != null){
                name = user.get("firstName").toString() + " ";
            }
            if(user.get("lastName") != null){
                name = name + user.get("lastName").toString();
            }
            firstAndLastName.setText(name);

        final Button follow = (Button) v.findViewById(R.id.btn_discover_follow);
        ParseHelper.enableButtonIfIsNotFollowing(user,follow);


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseHelper.followUser(user,follow);
            }
        });


        return v;
    }


}
