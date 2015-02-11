package com.application.challenge.challenge.main.challenges;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.ChallengesItem;
import com.application.challenge.challenge.domain.ChallengesListViewAdapter;
import com.application.challenge.challenge.domain.ExpandableHeightGridView;
import com.application.challenge.challenge.domain.ScrollListener;
import com.application.challenge.challenge.domain.SquareImageGridViewAdapter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListFragment extends android.support.v4.app.ListFragment{

    ChallengesListViewAdapter challengeListAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeListAdapter = new ChallengesListViewAdapter(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        setListAdapter(challengeListAdapter);
        challengeListAdapter.loadObjects();

        return v;
    }



}
