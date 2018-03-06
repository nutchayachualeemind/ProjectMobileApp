package project.appgame.lettryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import project.appgame.lettryquiz.Functions.Database;
import project.appgame.lettryquiz.Functions.Rank;
import project.appgame.lettryquiz.Functions.Score;

public class ScoreActivity extends AppCompatActivity {
    ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lstView = (ListView)findViewById(R.id.lstRanking);
        Database db = new Database(this);
        List<Rank> lstRanking = db.getRank();
        if(lstRanking.size() > 0) {
            Score adapter = new Score(this,lstRanking);
            lstView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

