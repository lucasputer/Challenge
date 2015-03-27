package com.application.challenge.challenge.main.commons.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.UserListViewAdapter;
import com.application.challenge.challenge.domain.adapter.UserSearchListViewAdapter;
import com.application.challenge.challenge.domain.custom.Interactions;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.FollowActivityObject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import de.greenrobot.event.EventBus;

public class SearchActivity extends ListActivity {

    UserSearchListViewAdapter userSearchListViewAdapter;
    UserListViewAdapter userListViewAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // get the action bar
         actionBar = getActionBar();

        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);



        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            actionBar.setTitle(R.string.title_activity_search);

            String searchedText = intent.getStringExtra(SearchManager.QUERY);

            userSearchListViewAdapter = new UserSearchListViewAdapter(this,ParseHelper.getUsersFromSearch(searchedText));
            ListView lv = (ListView) findViewById(android.R.id.list);

            lv.setAdapter(userSearchListViewAdapter);
            userSearchListViewAdapter.loadObjects();


        }else{
            final String type = intent.getStringExtra("type");
            ParseUser user = EventBus.getDefault().getStickyEvent(ParseUser.class);

            if(type.equals(Interactions.FOLLOWINGS.toString())){
                actionBar.setTitle(R.string.followings);
            }else if(type.equals(Interactions.FOLLOWERS.toString())){
                actionBar.setTitle(R.string.followers);
            }

            ParseQuery query = new ParseQuery<FollowActivityObject>("FollowActivity");
            query.include("toUser");
            query.include("fromUser");
            if(type.equals(Interactions.FOLLOWINGS.toString())){
                query.whereEqualTo("fromUser",user);
            }else if(type.equals(Interactions.FOLLOWERS.toString())){
                query.whereEqualTo("toUser",user);
            }

            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    if(e == null){
                        userListViewAdapter = new UserListViewAdapter(getApplicationContext(),list,type);
                        ListView lv = (ListView) findViewById(android.R.id.list);
                        lv.setAdapter(userListViewAdapter);
                    }
                }
            });


        }

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
