package project.appgame.lettryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import project.appgame.lettryquiz.Functions.Database;

public class DoneActivity extends AppCompatActivity {
    Button btnTryAgain;
    TextView txtResultScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        Database db = new Database(this);

        txtResultScore = (TextView) findViewById(R.id.txtTotalScore);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            int playCount = 0;
            if(totalQuestion == 10) {
                playCount = db.getPlayCount(0);
                playCount++;
                db.updatePlayCount(0,playCount);
            } else if(totalQuestion == 20) {
                playCount = db.getPlayCount(1);
                playCount++;
                db.updatePlayCount(1,playCount);
            } else {
                playCount = db.getPlayCount(2);
                playCount++;
                db.updatePlayCount(2,playCount);
            }

            int finalScore = score;
            txtResultScore.setText(String.format("Score : %d", finalScore));
            db.insertScore(finalScore);
        }
    }
}
