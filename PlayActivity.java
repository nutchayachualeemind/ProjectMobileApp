package project.appgame.lettryquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.appgame.lettryquiz.Functions.Database;
import project.appgame.lettryquiz.Functions.Question;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    final static long INTERVAL = 1000;
    final static long TIMEOUT = 12000;

    CountDownTimer mCountDown;
    List<Question> questionPlay = new ArrayList<>();
    Database db;
    int index=0, score=0, thisQuestion=0, totalQuestion, correctAnswer, count=0;
    String mode="";

    ProgressBar progressBar;
    int progressValue = 0;
    ImageView imageView;
    Button btnA,btnB,btnC,btnD;
    TextView txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            mode = extra.getString("MODE");
        }

        db = new Database(this);
        txtQuestion = (TextView)findViewById(R.id.txtQuestion);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageView = (ImageView)findViewById(R.id.question_flag);
        btnA=(Button)findViewById(R.id.btnAnswerA);
        btnB=(Button)findViewById(R.id.btnAnswerB);
        btnC=(Button)findViewById(R.id.btnAnswerC);
        btnD=(Button)findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        questionPlay = db.getQuestionMode(mode);
        totalQuestion = questionPlay.size();

        mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
                count = count+1000;
            }

            @Override
            public void onFinish() {
                if(count == TIMEOUT-1000) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                    builder.setMessage("Correct answer: " + questionPlay.get(index).getCorrectAnswer());
                    builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            showQuestion(++index);
                        }
                    });
                    builder.show();
                } else {
                    mCountDown.cancel();
                    showQuestion(++index);
                }
            }
        };
        showQuestion(index);
    }

    private void showQuestion(int index) {
        if(index < totalQuestion){
            thisQuestion++;
            txtQuestion.setText(String.format("%d/%d",thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;
            count = 0;

            int ImageId=this.getResources().getIdentifier(questionPlay.get(index).getImage().toLowerCase(),"drawable",getPackageName());
            imageView.setBackgroundResource(ImageId);
            btnA.setText(questionPlay.get(index).getAnswerA());
            btnB.setText(questionPlay.get(index).getAnswerB());
            btnC.setText(questionPlay.get(index).getAnswerC());
            btnD.setText(questionPlay.get(index).getAnswerD());

            mCountDown.start();
        } else {
            endGame();
        }
    }

    @Override
    public void onClick(View v) {
        mCountDown.cancel();

        if((index < totalQuestion) || (count==TIMEOUT-1000)){
            Button clickedButton = (Button)v;
            if (clickedButton.getText().equals(questionPlay.get(index).getCorrectAnswer())) {
                score += 10;
                correctAnswer++;
                showQuestion(++index);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                builder.setMessage("Correct answer: " + questionPlay.get(index).getCorrectAnswer());
                builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showQuestion(++index);
                    }
                });
                builder.show();
            }
        }
    }

    protected void onPause() {
        super.onPause();

        mCountDown.cancel();
        finish();
    }

    public void onBackPressed() {
        mCountDown.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit a game?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setProgress(0);
                progressValue = 0;
                count = 0;
                mCountDown.start();
            }
        });
        builder.show();
    }

    public void endGame() {
        Intent endGame = new Intent(PlayActivity.this, DoneActivity.class);
        Bundle dataSend = new Bundle();
        dataSend.putInt("SCORE", score);
        dataSend.putInt("TOTAL", totalQuestion);
        dataSend.putInt("CORRECT", correctAnswer);
        endGame.putExtras(dataSend);
        startActivity(endGame);
        finish();
    }
}

