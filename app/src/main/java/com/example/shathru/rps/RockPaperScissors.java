package com.example.shathru.rps;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.MediaPlayer;

public class RockPaperScissors extends ActionBarActivity implements OnClickListener {

    //Testing the backgroundMusic;
    private MediaPlayer backgroundMusic;
    //

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String spokenWord;
    boolean play = true;

    // Testing sound for the game. Including sound when rock wins the game.
    private SoundPool sounds;
    private int rock;
    //

    public enum Option {
        ROCK, PAPER, SCISSORS
    }

    public enum Result {
        WIN, LOSS, DRAW
    }


    //Custom AppBar
    private Toolbar toolbar;


    private Option userSelection;
    private Result gameResult;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        //Initiate backgroundMusic
        backgroundMusic = MediaPlayer.create(RockPaperScissors.this, R.raw.theme);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
        //

        //Initialize the sounds variables
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        rock = sounds.load(getApplicationContext(), R.raw.rock, 1);
        //

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock_paper_scissors);

        Button buttonRock = (Button) findViewById(R.id.buttonRock);
        Button buttonpaper = (Button) findViewById(R.id.buttonPaper);
        Button buttonScissors = (Button) findViewById(R.id.buttonScissors);
        ImageButton buttonHome = (ImageButton) findViewById(R.id.imageButtonHome);

        // Set click listening event for all buttons.
        buttonRock.setOnClickListener(this);
        buttonpaper.setOnClickListener(this);
        buttonScissors.setOnClickListener(this);
        buttonHome.setOnClickListener(this);

        //Call the custom AppBar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Speech Recognition related code
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }




    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
                    spokenWord = result.get(0);
                    txtSpeechInput.setText(spokenWord);

                    //zzzzzzzz
                    //txtSpeechInput.setText(result.get(0));
                    spokenWord = result.get(0);
                    //txtSpeechInput.setText(spokenWord);

                    //Try including the Game logic in here
                    if(spokenWord.equalsIgnoreCase("Rock"))
                        userSelection = Option.ROCK;
                    else if(spokenWord.equalsIgnoreCase("Paper"))
                        userSelection = Option.PAPER;
                    else if(spokenWord.equalsIgnoreCase("Scissors"))
                        userSelection = Option.SCISSORS;
                    else{
                        txtSpeechInput.setText("The word you spoke was "+spokenWord+" and it is not one in the game");
                    }



                    if(play) {
                        play();
                        showResults();
                    }
                    //zzzzzzzz
                }
                break;
            }

        }
    }





    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RockPaperScissors.this);
        builder.setCancelable(false);
        builder.setMessage("Hipity Hoo Blah!");
    }

    //Trying something new




    private void showResults() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RockPaperScissors.this);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        // Sets the right message according to result.
        if(gameResult == Result.LOSS) {
            builder.setMessage("You Loose!");
        } else if(gameResult == Result.WIN) {
            builder.setMessage("You Win!");
        } else if(gameResult == Result.DRAW) {
            builder.setMessage("It's a draw!");
        }

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar (AppBar) item clicks here. The action bar will automatically handle clicks
        //on the Home/Up Button so long as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        //noInspection Simplifiable If Statement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            if(backgroundMusic.isPlaying()){
                backgroundMusic.pause();
            }
            else {
                backgroundMusic.start();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        ImageView imageView = (ImageView) findViewById(R.id.imageViewAnswerUser);
        boolean play = true;

        switch (v.getId()) {
            case R.id.buttonRock:
                userSelection = Option.ROCK;
                imageView.setImageResource(R.drawable.rock);
                break;
            case R.id.buttonPaper:
                userSelection = Option.PAPER;
                imageView.setImageResource(R.drawable.paper);
                break;
            case R.id.buttonScissors:
                userSelection = Option.SCISSORS;
                imageView.setImageResource(R.drawable.scissors);
                break;
            case R.id.imageButtonHome:
                startActivity(new Intent(RockPaperScissors.this, RockPaperScissors.class)); // To go home.
                play = false;
                break;
        }

        if (play) {
            play();
            showResults();
        }
    }


    private void play() {
        // Generates a random play.
        int rand = ((int) (Math.random() * 10)) % 3;
        Option androidSelection = null;
        ImageView imageView = (ImageView) findViewById(R.id.ImageViewAnswerAndroid);

        // Sets the right image according to random selection.
        switch (rand) {
            case 0:
                androidSelection = Option.ROCK;
                imageView.setImageResource(R.drawable.rock);
                break;
            case 1:
                androidSelection = Option.PAPER;
                imageView.setImageResource(R.drawable.paper);
                break;
            case 2:
                androidSelection = Option.SCISSORS;
                imageView.setImageResource(R.drawable.scissors);
                break;
        }
        // Determine game result according to user selection and Android selection.
        if (androidSelection == userSelection) {
            sounds.play(rock, 1.0f, 1.0f, 0, 0, 1.5f);
            gameResult = Result.DRAW;
        } else if (androidSelection == Option.ROCK && userSelection == Option.SCISSORS) {
            sounds.play(rock, 1.0f, 1.0f, 0, 0, 1.5f);
            gameResult = Result.LOSS;
        } else if (androidSelection == Option.PAPER && userSelection == Option.ROCK) {

            sounds.play(rock, 1.0f, 1.0f, 0, 0, 1.5f);
            gameResult = Result.LOSS;
        } else if (androidSelection == Option.SCISSORS && userSelection == Option.PAPER) {
            sounds.play(rock, 1.0f, 1.0f, 0, 0, 1.5f);
            gameResult = Result.LOSS;
        } else {
            sounds.play(rock, 1.0f, 1.0f, 0, 0, 1.5f);
            gameResult = Result.WIN;
        }
    }
}