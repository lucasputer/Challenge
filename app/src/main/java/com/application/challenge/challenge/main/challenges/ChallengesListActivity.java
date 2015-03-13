package com.application.challenge.challenge.main.challenges;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.PictureListViewAdapter;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import de.greenrobot.event.EventBus;

public class ChallengesListActivity extends ListActivity {

    private ChallengeObject settedChallenge;
    private PictureListViewAdapter pictureListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_list);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        try{
            settedChallenge = EventBus.getDefault().getStickyEvent(ChallengeObject.class);
           // settedChallenge.fetchFromLocalDatastore();
            settedChallenge = settedChallenge.fetch();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Intentelo nuevamente más tarde.",Toast.LENGTH_SHORT).show();
            finish();
        }


        pictureListViewAdapter = new PictureListViewAdapter(getApplicationContext(),ParseHelper.getPicturesFromChallengeFactory(settedChallenge));

        ListView lv = (ListView) findViewById(android.R.id.list);

        lv.setAdapter(pictureListViewAdapter);

        pictureListViewAdapter.loadObjects();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_challenges_list, menu);
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

}
