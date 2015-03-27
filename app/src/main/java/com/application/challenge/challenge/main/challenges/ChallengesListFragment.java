package com.application.challenge.challenge.main.challenges;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.ChallengesSwipeListViewAdapter;
import com.application.challenge.challenge.domain.custom.Tabs;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.helper.TabHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.main.commons.fragment.ChallengeFragment;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListFragment extends ChallengeFragment{

    private ChallengesSwipeListViewAdapter challengeListAdapter;
    private SwipeListView swipeListView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      // challengeListAdapter = new ChallengesListViewAdapter(getActivity(), ParseHelper.getChallengeListQueryFactory());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view_swipe_challenges, container, false);


        TabHelper.setTab(Tabs.CHALLENGES);

        swipeListView = (SwipeListView) v.findViewById(R.id.example_lv_list);

        ParseQuery<ChallengeObject> query = ParseHelper.getChallengeListQuery();
        query.findInBackground(new FindCallback<ChallengeObject>() {
            @Override
            public void done(List<ChallengeObject> challengeObjects, ParseException e) {
                if(e==null){


                    //challengeList.addAll(challengeObjects);
                    challengeListAdapter = new ChallengesSwipeListViewAdapter(getActivity(),challengeObjects);

                    swipeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

                    swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
                        @Override
                        public void onOpened(int position, boolean toRight) {
                        }

                        @Override
                        public void onClosed(int position, boolean fromRight) {
                        }

                        @Override
                        public void onListChanged() {
                        }

                        @Override
                        public void onMove(int position, float x) {
                        }

                        @Override
                        public void onStartOpen(int position, int action, boolean right) {
                            Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
                        }

                        @Override
                        public void onStartClose(int position, boolean right) {
                            Log.d("swipe", String.format("onStartClose %d", position));
                        }



                        @Override
                        public void onClickFrontView(int position) {
                            Log.d("swipe", String.format("onClickFrontView %d", position));

                            swipeListView.openAnimate(position); //when you touch front view it will open

                        }

                        @Override
                        public void onClickBackView(int position) {
                            Log.d("swipe", String.format("onClickBackView %d", position));

                            swipeListView.closeAnimate(position);//when you touch back view it will close
                        }

                    });
                    swipeListView.setAdapter(challengeListAdapter);

                }
            }
        });

        return v;
    }

    @Override
    public void onPause(){
        swipeListView.closeOpenedItems();
        super.onPause();
    }





}
