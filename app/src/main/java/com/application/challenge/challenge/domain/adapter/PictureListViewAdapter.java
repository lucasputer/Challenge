package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.CircularImageView;
import com.application.challenge.challenge.domain.custom.SquareImageView;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by lucas on 16/1/15.
 */
public class PictureListViewAdapter extends ParseQueryAdapter<PhotoObject> {

    private Context cntxt;


    public PictureListViewAdapter(Context context, ParseQueryAdapter.QueryFactory<PhotoObject>  queryFactory){

        super(context,queryFactory);

        cntxt = context;

    }

    @Override
    public View getItemView(final PhotoObject photoObj, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.element_picture, null);
        }

        super.getItemView(photoObj, v, parent);

        SquareImageView photoImageView = (SquareImageView) v.findViewById(R.id.picture_image);
        CircularImageView thumbnailImageView = (CircularImageView) v.findViewById(R.id.circled_picture_profile_image);
        TextView usernameTextView = (TextView) v.findViewById(R.id.picture_username);
        final TextView pictureLikesAmount = (TextView) v.findViewById(R.id.picture_heart_amount);

        ParseHelper.loadPicture(cntxt, usernameTextView, thumbnailImageView, photoImageView, pictureLikesAmount, photoObj);

        v.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));


        final Button like = (Button) v.findViewById(R.id.btn_picture_heart);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseHelper.likePicture(getContext(),photoObj,pictureLikesAmount,like);
            }
        });

        return v;
    }

}
