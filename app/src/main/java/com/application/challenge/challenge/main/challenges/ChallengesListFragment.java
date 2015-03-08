package com.application.challenge.challenge.main.challenges;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.ChallengesListViewAdapter;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.main.camera.CameraActivity;
import com.application.challenge.challenge.main.picture.PictureActivity;

import de.greenrobot.event.EventBus;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);

                ChallengeObject challengeObject = challengeListAdapter.getItem(position);

                EventBus.getDefault().postSticky(challengeObject);

                startActivity(intent);
            }
        });
    }



}
