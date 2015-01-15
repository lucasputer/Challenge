package com.application.challenge.challenge.main.commons.fragment;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by lucas on 29/12/14.
 */
public class ChallengeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
