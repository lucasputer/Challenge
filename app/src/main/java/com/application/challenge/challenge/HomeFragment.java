package com.application.challenge.challenge;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.challenge.challenge.domain.ChallengeFragment;
import com.application.challenge.challenge.domain.Tabs;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.application.challenge.challenge.HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.application.challenge.challenge.HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends ChallengeFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentTabHost homeTabHost;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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

        View v = inflater.inflate(R.layout.fragment_home, container, false);

       setHomeTabs(v);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    private void setHomeTabs(View v){

        homeTabHost = (FragmentTabHost) v.findViewById(android.R.id.tabhost);
        homeTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_home);

        Bundle b = new Bundle();
        b.putString("key", Tabs.HOME_POPULAR.toString());
        homeTabHost.addTab(homeTabHost.newTabSpec(Tabs.HOME_POPULAR.toString())
                .setIndicator(createTabView("POPULARES")), TabFragment.class, b);

        b = new Bundle();
        b.putString("key", Tabs.HOME_FRIENDS.toString());
        homeTabHost.addTab(homeTabHost.newTabSpec(Tabs.HOME_FRIENDS.toString())
                .setIndicator(createTabView("AMIGOS")), TabFragment.class, b);
    }

    private View createTabView(final String text) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_tab_icon, null);
        TextView textView = (TextView) view.findViewById(R.id.home_tab_text);
        textView.setText(text);

        return view;
    }


}
