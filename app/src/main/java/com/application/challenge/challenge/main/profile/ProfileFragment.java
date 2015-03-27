package com.application.challenge.challenge.main.profile;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.Interactions;
import com.application.challenge.challenge.domain.helper.BlurHelper;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.main.camera.CameraActivity;
import com.application.challenge.challenge.main.commons.activity.SearchActivity;
import com.application.challenge.challenge.main.commons.fragment.ChallengeFragment;
import com.application.challenge.challenge.domain.custom.Tabs;
import com.application.challenge.challenge.main.commons.fragment.TabFragment;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseUser;

import de.greenrobot.event.EventBus;


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
    private ParseUser user;


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
        final View v = inflater.inflate(R.layout.fragment_profile, container, false);

        try{
            user = EventBus.getDefault().getStickyEvent(ParseUser.class);
        }catch(Exception e){
            user = ParseUser.getCurrentUser();
        }
        if(user == null){
            user = ParseUser.getCurrentUser();
        }

            user.fetchInBackground(new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    setProfileInformation(v,parseUser);

                }
            });

        setProfileInformation(v,user);

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
                .setIndicator(createTabView(R.drawable.btn_profile_grid, Tabs.PROFILE_GRID.toString())), ProfileGridViewFragment.class, b);

        b = new Bundle();
        b.putString("key", Tabs.PROFILE_LIST.toString());
        profileTabHost.addTab(profileTabHost.newTabSpec(Tabs.PROFILE_LIST.toString())
                .setIndicator(createTabView(R.drawable.btn_profile_list, Tabs.PROFILE_LIST.toString())), ProfileListFragment.class, b);

        b = new Bundle();
        b.putString("key", Tabs.PROFILE_HEART.toString());
        profileTabHost.addTab(profileTabHost.newTabSpec(Tabs.PROFILE_HEART.toString())
                .setIndicator(createTabView(R.drawable.btn_profile_heart, Tabs.PROFILE_HEART.toString())), ProfileGridViewLikedFragment.class, b);


    }


    private View createTabView(final int id, final String text) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.element_profile_tab_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.profile_tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));

        return view;
    }

    private void setProfileInformation(View v, final ParseUser currentUser){

        user = currentUser;

        String name = "";
        try{
            String firstName = currentUser.get("firstName").toString();
            String lastName = currentUser.get("lastName").toString();

            if(firstName != null && lastName != null){
                name = firstName + " " + lastName;
            }else{
                name = currentUser.getUsername();
            }
        }catch (Exception e){
            if(currentUser.get("displayName") != null){
                name = currentUser.get("displayName").toString();
            }else{
                name = "";
            }
        }

        TextView usernameTextView = (TextView) v.findViewById(R.id.txt_username_profile);
        usernameTextView.setText(name);


        ParseImageView profilePicture = (ParseImageView) v.findViewById(R.id.view_user_profile);
        try{
            ParseFile displayPictureThumbnail = currentUser.getParseFile("displayPictureThumbnail");
            if(displayPictureThumbnail != null){
                profilePicture.setParseFile(displayPictureThumbnail);
                profilePicture.loadInBackground();
            }
        }catch(Exception e){
            e.printStackTrace();
            profilePicture.setBackground(getResources().getDrawable(R.drawable.login_backround));
        }

        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.relative_user_profile);

        try {
            ParseFile p = currentUser.getParseFile("displayPicture");
            BlurHelper blurHelper = new BlurHelper();
            Drawable image = new BitmapDrawable(blurHelper.fastblur(BitmapFactory.decodeByteArray(p.getData(), 0, p.getData().length), 5));
            relativeLayout.setBackground(image);
        }catch(Exception e){
            e.printStackTrace();
        }

        TextView challenges = (TextView) v.findViewById(R.id.txt_challenges_amount_profile);
        ParseHelper.setPhotosCountForUser(challenges,currentUser);

        TextView following = (TextView) v.findViewById(R.id.txt_following_amount_profile);
        if(currentUser.get("followingCount") != null){
            following.setText(currentUser.get("followingCount").toString());
        }else{
            following.setText("0");
        }

        TextView followers = (TextView) v.findViewById(R.id.txt_followers_amount_profile);
        ParseHelper.setFollowersCountForUser(followers,currentUser);

        RelativeLayout relativeFollowing = (RelativeLayout)v.findViewById(R.id.relative_following_profile);
        relativeFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(getActivity(), SearchActivity.class);
                intent.putExtra("type", Interactions.FOLLOWINGS.toString());
                EventBus.getDefault().postSticky(currentUser);
                startActivity(intent);
            }
        });

        RelativeLayout relativeFollowers = (RelativeLayout)v.findViewById(R.id.relative_followers_profile);
        relativeFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(getActivity(), SearchActivity.class);
                intent.putExtra("type", Interactions.FOLLOWERS.toString());
                intent.putExtra("user",currentUser.getObjectId());
                startActivity(intent);
            }
        });

    }

}
