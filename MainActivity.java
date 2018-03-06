package project.appgame.lettryquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import project.appgame.lettryquiz.Functions.Database;
import project.appgame.lettryquiz.Functions.Level;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView txtMode;
    Button btnPlay,btnScore;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        seekBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        txtMode = (TextView)findViewById(R.id.txtMode);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnScore = (Button)findViewById(R.id.btnScore);

        db = new Database(this);
        try {
            db.createDataBase();
        } catch (IOException e){
            e.printStackTrace();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0) {
                    txtMode.setText(Level.MODE.EASY.toString());
                } else if(progress == 1) {
                    txtMode.setText(Level.MODE.MEDIUM.toString());
                } else {
                    txtMode.setText(Level.MODE.HARD.toString());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PlayActivity.class);
                intent.putExtra("MODE",getPlayMode());
                startActivity(intent);
                finish();
            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ScoreActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getPlayMode() {
        if(seekBar.getProgress()==0) {
            return Level.MODE.EASY.toString();
        } else if(seekBar.getProgress()==1){
            return Level.MODE.MEDIUM.toString();
        } else {
            return Level.MODE.HARD.toString();
        }
    }

    public void onBackPressed() {
        finish();
    }
}
