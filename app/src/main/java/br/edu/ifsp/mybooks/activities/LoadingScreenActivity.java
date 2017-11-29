package br.edu.ifsp.mybooks.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import br.edu.ifsp.mybooks.R;

public class LoadingScreenActivity extends AppCompatActivity {

    private ProgressBar progress;
    private Handler handler;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        progress = (ProgressBar) findViewById(R.id.progressBarLoading);

        handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i+=7){
                    final int value = i;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(value);
                        }
                    });
                }
                Intent intent = new Intent(LoadingScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        new Thread(runnable).start();

        mp = MediaPlayer.create(getBaseContext(), R.raw.bubblesound);
        mp.start();

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        v.vibrate(500);
    }

}
