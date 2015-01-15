package com.application.challenge.challenge.main.home;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.ScrollListener;
import com.application.challenge.challenge.domain.SquareImageGridViewAdapter;
import com.application.challenge.challenge.main.commons.fragment.ChallengeFragment;
import com.application.challenge.challenge.main.commons.fragment.GridViewFragment;

import java.util.ArrayList;
import java.util.List;


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

        GridView gridView = (GridView)v.findViewById(R.id.gridview);

        //TODO traer las imagenes que hay que mostrar
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("http://thetravellette.com/wp-content/uploads/2013/05/Times-square.jpg");
        urls.add("http://matylda.me/wp-content/uploads/2012/05/tumblr_m37zlq2ySY1qc1wico1_1280.jpg");
        urls.add("http://matylda.me/wp-content/uploads/2012/05/tumblr_m37mddGOQi1qc1wico1_1280.jpg");
        urls.add("http://farm5.static.flickr.com/4150/5450624517_534abcf8e4_m.jpg");
        urls.add("http://40.media.tumblr.com/31d7f5a51fb8b1f40bcbadf6197d4ca4/tumblr_mox2wptnmM1r1thfzo4_1280.jpg");
        urls.add("http://farm4.static.flickr.com/3677/9519750899_8ba1b78210_m.jpg");

        gridView.setAdapter(new SquareImageGridViewAdapter(getActivity(),urls));
        gridView.setOnScrollListener(new ScrollListener(getActivity()));
        return v;
    }


}
