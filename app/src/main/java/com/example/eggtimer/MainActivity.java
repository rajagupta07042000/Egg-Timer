package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     TextView timerTextView;
     SeekBar timerSeekBar;
     Button goButton;
     CountDownTimer countDownTimer;
     boolean counterIsActive=false;//counter not active
     public void resetTimer()//to automatically set after counter is finished
     {
         timerTextView.setText("00:30");
         timerSeekBar.setProgress(30);
         timerSeekBar.setEnabled(true);//set enable sto work with seekbar
         countDownTimer.cancel();//stops to continue timer
         goButton.setText("Go!");
         counterIsActive=false;
     }
    public void buttonClicked(View view)
    {
        if(counterIsActive)//if(counter is active)
        {
          resetTimer();
        }
        else
        {
            counterIsActive=true;
            timerSeekBar.setEnabled(false);
            goButton.setText("Stop!");
             countDownTimer=new CountDownTimer(timerSeekBar.getProgress()*1000+100,1000)
            {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }

    }
    public void updateTimer(int secondsLeft)
    {
        int minutes=secondsLeft/60;
        int seconds=secondsLeft-(minutes*60);

        String secondsString=Integer.toString(seconds);

        if(seconds<10)//if( a complete minute)
        {
            secondsString="0"+secondsString;
        }
        timerTextView.setText(Integer.toString(minutes)+":"+secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar=findViewById(R.id.timerSeekBar);
        timerTextView=findViewById(R.id.countdownTextView);
        goButton=findViewById(R.id.goButton);
        timerSeekBar.setMax(600);//600 sec
        timerSeekBar.setProgress(30);//30sec

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
