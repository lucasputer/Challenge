package com.application.challenge.challenge.main.commons.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.main.commons.application.ChallengeApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by lucas on 29/12/14.
 */
public class ChallengeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Tracker tracker;
    private String activityId;
    private String fragmentId;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//
//    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        if (addToBackStack) {
//            transaction.addToBackStack(null);
//        }
//        transaction.replace(R.id.container_framelayout, fragment);
//        transaction.commit();
//        getChildFragmentManager().executePendingTransactions();
//    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        this.tracker = ((ChallengeApplication) getActivity().getApplication()).getTracker(
                ChallengeApplication.TrackerName.APP_TRACKER);

        this.fragmentId = getClass().getSimpleName();
        this.activityId = getActivity().getClass().getSimpleName();
    }

    @Override
    public void onResume() {

        super.onResume();

        tracker.setScreenName(fragmentId);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
