package com.example.shathru.rps;

import android.content.Intent;
import android.graphics.Outline;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import android.support.v7.widget.Toolbar;

import com.example.shathru.rps.NavigationDrawerFragment;


public class MainPage extends ActionBarActivity implements View.OnClickListener {

    //MediaPlayer backgroundMusic;

    Button buttonToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_page);
        setContentView(R.layout.activity_main_appbar);
        buttonToStart = (Button) findViewById(R.id.play);
        buttonToStart.setOnClickListener(this);

        //Background Music
       /* backgroundMusic = MediaPlayer.create(MainPage.this, R.raw.theme);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();*/

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //Will show the home button in the navigation drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Starting the navigation Drawer
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


    }

    /*
        //Tell the game when to stop the backgroundMusic because nowhere we've mentioned the app when to stop.
        //This will be the last class that will run when the application is closed.

        @Override
        protected void onPause() {
            super.onPause();
            backgroundMusic.release();
            finish();
        }

    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.play:
                startActivity(new Intent(MainPage.this, RockPaperScissors.class));
        }
    }
}
