package com.application.challenge.challenge;

import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.challenge.challenge.domain.ChallengeFragment;


public class MainActivity extends FragmentActivity implements ChallengeFragment.OnFragmentInteractionListener {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_tabs);
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Bundle b = new Bundle();
        b.putString("key", "home");
        mTabHost.addTab(mTabHost.newTabSpec("home")
                .setIndicator(createTabView(R.drawable.btn_home, "Home", false)), HomeFragment.class, b);
        b = new Bundle();

        b.putString("key", "challenges");
        mTabHost.addTab(mTabHost.newTabSpec("challenges")
                .setIndicator(createTabView(R.drawable.btn_challenges, "Challenges", false)), HomeFragment.class, b);
        b = new Bundle();

        b.putString("key", "Camara");
        mTabHost.addTab(mTabHost.newTabSpec("camara")
                .setIndicator(createTabView(R.drawable.btn_camera,"Camara",true)), HomeFragment.class, b);

        b.putString("key", "search");
        mTabHost.addTab(mTabHost.newTabSpec("search")
                .setIndicator(createTabView(R.drawable.btn_search,"search",false)), HomeFragment.class, b);
        b = new Bundle();


        b.putString("key", "Perfil");
        mTabHost.addTab(mTabHost.newTabSpec("perfil").setIndicator(createTabView(R.drawable.btn_profile,"Perfil",false)),
                ProfileFragment.class, b);
        b = new Bundle();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
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

    private View createTabView(final int id, final String text, boolean isCamera) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));



        if(isCamera){
            view.setBackgroundColor(Color.parseColor("#dc6423"));
            view.invalidate();
        }
        return view;
    }
}
