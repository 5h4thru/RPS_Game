package com.example.shathru.rps;

import android.content.Intent;
import android.graphics.Outline;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;


public class MainPage extends ActionBarActivity implements View.OnClickListener {

    MediaPlayer backgroundMusic;

    Button buttonToStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        buttonToStart = (Button) findViewById(R.id.play);
        buttonToStart.setOnClickListener(this);

        //Background Music
        backgroundMusic = MediaPlayer.create(MainPage.this, R.raw.rock);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

    }

    //Tell the game when to stop the backgroundMusic because nowhere we've mentioned the app when to stop.
    //This will be the last class that will run when the application is closed.

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.release();
        finish();
    }


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
