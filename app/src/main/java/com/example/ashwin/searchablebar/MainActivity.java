package com.example.ashwin.searchablebar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;
    TextView queryText, voice;
    private boolean mVoiceSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //initialization
        queryText = (TextView) findViewById(R.id.queryText);
        voice = (TextView) findViewById(R.id.voice_search);

        voice.setText("Voice input: " + String.valueOf(mVoiceSearch));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo( getComponentName() )); //new ComponentName(this, MainActivity.class)
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //user pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //user changed the text
        queryText.setText(newText);

        voice.setText("Voice input: " + String.valueOf(mVoiceSearch));
        mVoiceSearch = false;

        return false;
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            //gets the search query from the voice recognizer intent
            String query = intent.getStringExtra(SearchManager.QUERY);

            //set voiceSearch = true
            mVoiceSearch = true;

            //set the search box text to the received query but does not submit the search
            searchView.setQuery(query, false);
        }
    }

}
