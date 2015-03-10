package com.application.challenge.challenge.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.ExpandableHeightGridView;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.listener.ScrollListener;
import com.application.challenge.challenge.domain.adapter.SquareImageGridViewAdapter;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.main.commons.fragment.GridViewFragment;
import com.application.challenge.challenge.main.picture.PictureActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class HomePopularGridViewFragment extends GridViewFragment {

    SquareImageGridViewAdapter adapter;

    public static HomePopularGridViewFragment newInstance() {
        HomePopularGridViewFragment fragment = new HomePopularGridViewFragment();
        return fragment;
    }

    public HomePopularGridViewFragment() {
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

        final ExpandableHeightGridView gridView = (ExpandableHeightGridView)v.findViewById(R.id.gridview);

        final ArrayList<PhotoObject> photoArray = new ArrayList<PhotoObject>();

            ParseQuery<PhotoObject> query = ParseHelper.getPopularPictures();
            query.findInBackground(new FindCallback<PhotoObject>() {
                @Override
                public void done(List<PhotoObject> photoObjects, ParseException e) {
                    if (e == null){

                        photoArray.addAll(photoObjects);
                        adapter = new SquareImageGridViewAdapter(getActivity(),photoArray);
                        gridView.setAdapter(adapter);
                        gridView.setOnScrollListener(new ScrollListener(getActivity()));
                    }
                }
            });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), PictureActivity.class);
                PhotoObject ph = adapter.getItem(position);

                EventBus.getDefault().postSticky(adapter.getItem(position));

                startActivity(intent);

            }
        });

        v.setOverScrollMode(View.OVER_SCROLL_NEVER);


        return v;
    }


}
