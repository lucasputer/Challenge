package com.application.challenge.challenge.main.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.application.challenge.challenge.domain.adapter.DiscoverListViewAdapter;
import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.PictureListViewAdapter;
import com.application.challenge.challenge.domain.custom.Tabs;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.helper.TabHelper;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class DiscoverListFragment extends android.support.v4.app.ListFragment{


    DiscoverListViewAdapter discoverListViewAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseQueryAdapter.QueryFactory<ParseUser> p = ParseHelper.getDiscoverUsers();
        discoverListViewAdapter = new DiscoverListViewAdapter(getActivity(), p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        TabHelper.setTab(Tabs.SEARCH);

        ListView lv = (ListView) v.findViewById(android.R.id.list);

        lv.setAdapter(discoverListViewAdapter);
        discoverListViewAdapter.loadObjects();

        return v;
    }


}
