package com.application.challenge.challenge.main.profile;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.main.commons.fragment.ChallengeFragment;
import com.parse.ParseUser;

import de.greenrobot.event.EventBus;

public class ProfileActivity extends FragmentActivity implements ChallengeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_profile);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        //Remuevo el challenge guardado como challenge para la proxima foto, ya que estoy accediendo a la camara por otro lado
        if(EventBus.getDefault().getStickyEvent(ParseUser.class) != null){
            EventBus.getDefault().removeStickyEvent(ParseUser.class);
        }
        super.onDestroy();
    }

    public void onFragmentInteraction(Uri uri){
    }

}
