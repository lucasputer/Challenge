package com.application.challenge.challenge.main.challenges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.ChallengesListViewAdapter;
import com.application.challenge.challenge.domain.helper.ParseHelper;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListFragment extends android.support.v4.app.ListFragment{

    ChallengesListViewAdapter challengeListAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeListAdapter = new ChallengesListViewAdapter(getActivity(), ParseHelper.getChallengeListQuery());

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
