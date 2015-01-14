package com.application.challenge.challenge;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.application.challenge.challenge.domain.ChallengeFragment;
import com.application.challenge.challenge.domain.Tabs;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends ChallengeFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FragmentTabHost profileTabHost;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        setProfileTabs(v);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void setProfileTabs(View v){

        profileTabHost = (FragmentTabHost) v.findViewById(android.R.id.tabhost);
        profileTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_profile);

        Bundle b = new Bundle();
        b.putString("key", Tabs.PROFILE_GRID.toString());
        profileTabHost.addTab(profileTabHost.newTabSpec(Tabs.PROFILE_GRID.toString())
                .setIndicator(createTabView(R.drawable.btn_profile_grid, Tabs.PROFILE_GRID.toString())), TabFragment.class, b);

        b = new Bundle();
        b.putString("key", Tabs.PROFILE_LIST.toString());
        profileTabHost.addTab(profileTabHost.newTabSpec(Tabs.PROFILE_LIST.toString())
                .setIndicator(createTabView(R.drawable.btn_profile_list, Tabs.PROFILE_LIST.toString())), TabFragment.class, b);

        b = new Bundle();
        b.putString("key", Tabs.PROFILE_HEART.toString());
        profileTabHost.addTab(profileTabHost.newTabSpec(Tabs.PROFILE_HEART.toString())
                .setIndicator(createTabView(R.drawable.btn_profile_heart, Tabs.PROFILE_HEART.toString())), TabFragment.class, b);


    }


    private View createTabView(final int id, final String text) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_tab_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.profile_tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));

        return view;
    }

}