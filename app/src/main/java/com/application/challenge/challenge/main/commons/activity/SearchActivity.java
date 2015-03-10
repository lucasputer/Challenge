package com.application.challenge.challenge.main.commons.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.UserSearchListViewAdapter;
import com.application.challenge.challenge.domain.helper.ParseHelper;

public class SearchActivity extends ListActivity {

    UserSearchListViewAdapter userSearchListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // get the action bar
        ActionBar actionBar = getActionBar();

        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_search);



        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchedText = intent.getStringExtra(SearchManager.QUERY);

            userSearchListViewAdapter = new UserSearchListViewAdapter(this,ParseHelper.getUsersFromSearch(searchedText));
            ListView lv = (ListView) findViewById(android.R.id.list);

            lv.setAdapter(userSearchListViewAdapter);
            userSearchListViewAdapter.loadObjects();

        }

    }


}
