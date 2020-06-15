package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView queText;
    private TextView ansText;
    private TextView timer;
    private TextView score;
    private int seconds;
    private boolean stopTimer=false;
    private ImageButton correct;
    private ImageButton incorrect;
    private boolean isCorrect=true;
    private int scoreCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        queText=findViewById(R.id.question);
        ansText=findViewById(R.id.answer);
        timer=findViewById(R.id.timer);
        score=findViewById(R.id.score);
        correct = findViewById(R.id.correct);
        incorrect=findViewById(R.id.incorrect);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(true);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(false);
            }
        });
        seconds=59;
        startTimer();
        generateQuestion();
    }

    private void verifyAnswer(boolean choice) {
        if (isCorrect == choice)
            scoreCount+=5;
        else
            scoreCount-=1;
        score.setText("SCORE "+scoreCount);
        generateQuestion();
    }

    private void startTimer() {
        final Handler handler = new Handler();
        final boolean post = handler.post(new Runnable() {
            @Override
            public void run() {
                timer.setText("TIME :"+seconds);
                seconds--;
                if(seconds<0)
                {
                    stopTimer=true;
                    timer.setText("TIME :"+0);
                    timer.setBackgroundColor(Color.RED);
                    Intent intent=new Intent(GameActivity.this,ScoreActivity.class);
                    intent.putExtra("score",scoreCount);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                if (stopTimer== false) {
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void generateQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(100);
        int b = rand.nextInt(100);
        int result = a + b;
        isCorrect=true;
        float f = rand.nextFloat();
        if (f > 0.5f) {
            result += rand.nextInt(100);
            isCorrect = false;
        }
        queText.setText(a + "+" + b);
        ansText.setText("=" + result);
    }

}
