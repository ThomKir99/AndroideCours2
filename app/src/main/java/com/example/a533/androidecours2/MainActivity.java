package com.example.a533.androidecours2;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private int maxVolume =50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer =MediaPlayer.create(this,R.raw.farm);
        mediaPlayer.setLooping(false);

        setProgressBarProgression();
        setVolumeBar();
    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(getApplicationContext(),"Complete",Toast.LENGTH_SHORT).show();
        }
    });
        setListener();
    }

    private void setVolumeBar() {

        SeekBar seekBarVolume = findViewById(R.id.seekBar_volume);
        seekBarVolume.setProgress(seekBarVolume.getMax()/2);
            changeVolume(seekBarVolume.getProgress());
    }

    private void setProgressBarProgression() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
                seekBarMedia.setProgress(mediaPlayer.getCurrentPosition() *100 / mediaPlayer.getDuration());
                handler.postDelayed(this,100);
            }
        },1000);

    }

    public void setListener(){

        findViewById(R.id.btn_Play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              playSound();
            }
        });
        findViewById(R.id.btn_Pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pauseSound();
            }
        });
        setSeekBarListener();
    }

    private void setSeekBarListener(){

        setProgressBarListener();
        setVolumeListener();
    }

    private void setVolumeListener() {
        SeekBar seekBarVolume = findViewById(R.id.seekBar_volume);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    changeVolume(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void changeVolume(int progress) {
        float log1 = (float)(Math.log(maxVolume-progress)/Math.log(maxVolume));
        mediaPlayer.setVolume(1-log1,1-log1);
    }

    private void setProgressBarListener() {

        SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
        seekBarMedia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekInMedia(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void seekInMedia(int progress) {
        mediaPlayer.seekTo(progress* mediaPlayer.getDuration() /100);

    }

    private void playSound(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    private void pauseSound(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
}
