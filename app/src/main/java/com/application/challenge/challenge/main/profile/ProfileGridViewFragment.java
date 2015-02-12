package com.application.challenge.challenge.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.ExpandableHeightGridView;
import com.application.challenge.challenge.domain.adapter.SquareImageGridViewAdapter;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.main.commons.fragment.GridViewFragment;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;


public class ProfileGridViewFragment extends GridViewFragment {

    public static ProfileGridViewFragment newInstance() {
        ProfileGridViewFragment fragment = new ProfileGridViewFragment();
        return fragment;
    }

    public ProfileGridViewFragment() {
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

        ExpandableHeightGridView gridView = (ExpandableHeightGridView)v.findViewById(R.id.gridview);


        ArrayList<PhotoObject> photoArray = new ArrayList<PhotoObject>();

        try {
            ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
            query.whereEqualTo("user", ParseUser.getCurrentUser());
            query.orderByDescending("createdAt");
            photoArray.addAll(query.find());
        }catch (ParseException e){
            e.printStackTrace();
        }


        gridView.setNumColumns(3);
        gridView.setExpanded(true);
        gridView.setAdapter(new SquareImageGridViewAdapter(getActivity(), photoArray));
        //gridView.setOnScrollListener(new ScrollListener(getActivity()));

        return v;
    }


}
