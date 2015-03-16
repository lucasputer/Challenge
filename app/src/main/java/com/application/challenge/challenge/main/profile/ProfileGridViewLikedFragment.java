package com.application.challenge.challenge.main.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.SquareImageGridViewAdapter;
import com.application.challenge.challenge.domain.custom.ExpandableHeightGridView;
import com.application.challenge.challenge.domain.model.LikeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.main.commons.fragment.GridViewFragment;
import com.application.challenge.challenge.main.picture.PictureActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class ProfileGridViewLikedFragment extends GridViewFragment {

    SquareImageGridViewAdapter adapter;
    ExpandableHeightGridView gridView;

    public static ProfileGridViewLikedFragment newInstance() {
        ProfileGridViewLikedFragment fragment = new ProfileGridViewLikedFragment();
        return fragment;
    }

    public ProfileGridViewLikedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = super.onCreateView(inflater, container, savedInstanceState);

        gridView = (ExpandableHeightGridView)v.findViewById(R.id.gridview);


        final ArrayList<PhotoObject> photoArray = new ArrayList<PhotoObject>();

        ParseUser user;
        try{
            user = EventBus.getDefault().getStickyEvent(ParseUser.class);
        }catch(Exception e){
            user = ParseUser.getCurrentUser();
        }
        if(user == null){
            user = ParseUser.getCurrentUser();
        }

        ParseQuery<LikeObject> query = new ParseQuery<LikeObject>("Like");
        query.whereEqualTo("user", user);
        query.orderByDescending("createdAt");
        query.include("photo");
        query.findInBackground(new FindCallback<LikeObject>() {
            @Override
            public void done(List<LikeObject> likeObjects, ParseException e) {
                if(e==null){
                    for(LikeObject lo:likeObjects){
                        photoArray.add((PhotoObject)lo.getPhoto());
                    }
                    adapter = new SquareImageGridViewAdapter(getActivity(), photoArray);
                    configureGridView(photoArray);
                }

            }
        });



        //gridView.setOnScrollListener(new ScrollListener(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), PictureActivity.class);
                EventBus.getDefault().postSticky(adapter.getItem(position));

                startActivity(intent);

            }
        });

        return v;
    }

    private void configureGridView(final ArrayList<PhotoObject> photoArray){
        adapter = new SquareImageGridViewAdapter(getActivity(), photoArray);
        gridView.setNumColumns(3);
        gridView.setExpanded(true);
        gridView.setAdapter(adapter);
    }
}
