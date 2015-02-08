package com.application.challenge.challenge.main.commons.fragment;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.application.challenge.challenge.R;

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
