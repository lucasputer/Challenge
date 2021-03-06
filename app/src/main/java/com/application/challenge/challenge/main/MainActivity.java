package com.application.challenge.challenge.main;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.main.camera.CameraLoaderFragment;
import com.application.challenge.challenge.main.challenges.ChallengesListFragment;
import com.application.challenge.challenge.main.commons.fragment.ChallengeFragment;
import com.application.challenge.challenge.domain.custom.Tabs;
import com.application.challenge.challenge.main.commons.activity.ChallengeActionBarActivity;
import com.application.challenge.challenge.main.commons.application.ChallengeApplication;
import com.application.challenge.challenge.main.discover.DiscoverListFragment;
import com.application.challenge.challenge.main.home.HomeFragment;
import com.application.challenge.challenge.main.profile.ProfileFragment;
import com.parse.ParseUser;


public class MainActivity extends ChallengeActionBarActivity implements ChallengeFragment.OnFragmentInteractionListener{

    private FragmentTabHost menuTabHost;
    private static final int CAMERA_INDEX = 2;
    private String selectedTab = Tabs.HOME.toString();
    private TabHost.OnTabChangeListener listener;
    private ChallengeApplication challengeApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_partial_bottom_tabs);
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
        challengeApplication = ((ChallengeApplication)getApplicationContext());
        setMenuTabs();
    }


    @Override
    protected void onResume(){
        super.onResume();
        listener.onTabChanged(selectedTab);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.action_search) + "</font>"));



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if( id == R.id.action_logout){
            ParseUser.logOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }

    public void onFragmentInteraction(Uri uri){
    }

    private View createTabView(final int id, final String text) {
        View view = LayoutInflater.from(this).inflate(R.layout.element_menu_tab_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));

        if(text == Tabs.CAMERA.toString()){
            View lineView = (View) view.findViewById(R.id.tab_line);
            lineView.setBackgroundColor(Color.parseColor("#dc6423"));
            view.setBackgroundColor(Color.parseColor("#dc6423"));
            view.invalidate();
        }else if(text == Tabs.HOME.toString()){
            view.setBackgroundColor(Color.parseColor("#0a0a0b"));
            view.invalidate();
        }
        return view;
    }

    private void setMenuTabs(){

        //TODO Pasar todos los set tabs a un TabHelper que implemente esto y que tenga createTabView para ser overrideado


        menuTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        menuTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);



        Bundle b = new Bundle();
        b.putString("key", Tabs.HOME.toString());
        menuTabHost.addTab(menuTabHost.newTabSpec(Tabs.HOME.toString())
                .setIndicator(createTabView(R.drawable.btn_home, Tabs.HOME.toString())), HomeFragment.class, b);
        b = new Bundle();

        b.putString("key", Tabs.CHALLENGES.toString());
        menuTabHost.addTab(menuTabHost.newTabSpec(Tabs.CHALLENGES.toString())
                .setIndicator(createTabView(R.drawable.btn_challenges, Tabs.CHALLENGES.toString())), ChallengesListFragment.class, b);
        b = new Bundle();

        b.putString("key", Tabs.CAMERA.toString());
        menuTabHost.addTab(menuTabHost.newTabSpec(Tabs.CAMERA.toString())
                .setIndicator(createTabView(R.drawable.btn_camera, Tabs.CAMERA.toString())),CameraLoaderFragment.class,b);
        //Aca tengo que poner un fragment que lo unico que hace es empezar CameraActivity ya que necesito un fragment si o si


                b.putString("key", Tabs.SEARCH.toString());
        menuTabHost.addTab(menuTabHost.newTabSpec(Tabs.SEARCH.toString())
                .setIndicator(createTabView(R.drawable.btn_search, Tabs.SEARCH.toString())), DiscoverListFragment.class, b);
        b = new Bundle();


        b.putString("key", Tabs.PROFILE.toString());
        menuTabHost.addTab(menuTabHost.newTabSpec(Tabs.PROFILE.toString()).setIndicator(createTabView(R.drawable.btn_profile,Tabs.PROFILE.toString())),
                ProfileFragment.class, b);
        b = new Bundle();

        menuTabHost.getTabWidget().setDividerDrawable(R.drawable.empty_tab_divider);


        listener = new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                for (int j = 0; j < 5; j++) {
                    if (j != CAMERA_INDEX) {
                        menuTabHost.getTabWidget().getChildTabViewAt(j).setBackgroundColor(Color.parseColor("#25292c"));
                    }
                }

                if (tabId != Tabs.CAMERA.toString()) {
                    selectedTab = tabId;
                    challengeApplication.setSelectedMenuTab(menuTabHost.getCurrentTab());
                    menuTabHost.getTabWidget().getChildTabViewAt(menuTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0a0a0b"));

                }

            }
        };
        menuTabHost.setOnTabChangedListener(listener);
    }

    public int getSelectedMenuTab(){
        return challengeApplication.getSelectedMenuTab();
    }



}
