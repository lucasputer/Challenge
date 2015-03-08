package com.application.challenge.challenge.main.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.main.MainActivity;
import com.application.challenge.challenge.R;
import com.application.challenge.challenge.main.commons.fragment.ChallengeFragment;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.application.challenge.challenge.main.commons.fragment.ChallengeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CameraLoaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraLoaderFragment extends ChallengeFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraLoaderFragment newInstance(String param1, String param2) {
        CameraLoaderFragment fragment = new CameraLoaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CameraLoaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        //Remuevo el challenge guardado como challenge para la proxima foto, ya que estoy accediendo a la camara por otro lado
        if(EventBus.getDefault().getStickyEvent(ChallengeObject.class) != null){
            EventBus.getDefault().removeStickyEvent(ChallengeObject.class);
        }

        Intent cameraIntent = new Intent().setClass(getActivity(), CameraActivity.class);
        startActivity(cameraIntent);

        TabHost menuTabHost = (FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
        MainActivity mainActivity = (MainActivity)getActivity();
        menuTabHost.setCurrentTab(mainActivity.getSelectedMenuTab());

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }





}
