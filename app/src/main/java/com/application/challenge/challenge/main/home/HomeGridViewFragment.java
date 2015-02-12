package com.application.challenge.challenge.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.ExpandableHeightGridView;
import com.application.challenge.challenge.domain.listener.ScrollListener;
import com.application.challenge.challenge.domain.adapter.SquareImageGridViewAdapter;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.main.commons.fragment.GridViewFragment;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class HomeGridViewFragment extends GridViewFragment {

    public static HomeGridViewFragment newInstance() {
        HomeGridViewFragment fragment = new HomeGridViewFragment();
        return fragment;
    }

    public HomeGridViewFragment() {
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
            query.setLimit(20);
            query.orderByDescending("createdAt");
            photoArray.addAll(query.find());
        }catch (ParseException e){
            e.printStackTrace();
        }

        gridView.setAdapter(new SquareImageGridViewAdapter(getActivity(),photoArray));
        gridView.setOnScrollListener(new ScrollListener(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                

            }
        });
        return v;
    }


}
