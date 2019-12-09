package com.aryan.quizapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;
import java.util.Timer;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBarView;
    TextView tvQuestion, tvTimer, tvPoints, tvUsername;
    Button btnOpt1, btnOpt2, btnOpt3, btnOpt4;

    SQLiteDatabase db;
    dbHelper helper;
    Boolean clickable = true;

    List<QuestionModel> list;
    QuestionModel currentQuestion;
    int points = 0, questionCounter = 0, correctAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Intent intent = getIntent();
        tvUsername.setText(intent.getStringExtra("username") + "");

        helper = new dbHelper(this);
        db = helper.getWritableDatabase();

        list =  helper.getAllQuestions(db);
//        Collections.shuffle(list);
        loadQuestion();

        btnOpt1.setOnClickListener(this);
        btnOpt2.setOnClickListener(this);
        btnOpt3.setOnClickListener(this);
        btnOpt4.setOnClickListener(this);
    }

    public void loadQuestion(){

//        btnOpt1.setClickable(true);
//        btnOpt2.setClickable(true);
//        btnOpt3.setClickable(true);
//        btnOpt4.setClickable(true);

        if(questionCounter < list.size()) {
            currentQuestion = list.get(questionCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            btnOpt1.setText(currentQuestion.getOption1());
            btnOpt2.setText(currentQuestion.getOption2());
            btnOpt3.setText(currentQuestion.getOption3());
            btnOpt4.setText(currentQuestion.getOption4());

            correctAnswer = currentQuestion.getAnswerNo();
            questionCounter ++;
        }
        else if(questionCounter == list.size()){

            Intent showPoints = new Intent(MainActivity.this, TempActivity.class);
            showPoints.putExtra("user", tvUsername.getText().toString());
            showPoints.putExtra("points", tvPoints.getText().toString());
            startActivity(showPoints);
        }

    }

    @Override
    public void onClick(View v) {
        if(clickable) {
            switch (v.getId()) {
                case R.id.btnOpt1:
                    clickable = false;
                    hideButton();
                    checkAns(v, 1);
                    break;
                case R.id.btnOpt2:
                    clickable = false;
                    hideButton();
                    checkAns(v, 2);
                    break;
                case R.id.btnOpt3:
                    clickable = false;
                    hideButton();
                    checkAns(v, 3);
                    break;
                case R.id.btnOpt4:
                    clickable = false;
                    hideButton();
                    checkAns(v, 4);
                    break;
            }
        }


    }

    public void hideButton(){
//        btnOpt1.setClickable(false);
//        btnOpt2.setClickable(false);
//        btnOpt3.setClickable(false);
//        btnOpt4.setClickable(false);
    }

    public void checkAns(View view, int ans){
        if(ans == correctAnswer){
            Snackbar snackbar = Snackbar.make(view, "Correct Answer", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null);

            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.rightAnswer));
            snackbar.show();
            points += 10;
            tvPoints.setText(""+points);
        }
        else{
            Snackbar snackbar = Snackbar.make(view, "Worng Answer", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null);

            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.wrongAnswer));
            snackbar.show();
        }
        nextQuestion();
    }

    private void nextQuestion(){
        Thread timer = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadQuestion();
                            clickable = true;
                        }
                    });
                }
            }
        };
        timer.start();
    }

    private void initView(){

        progressBarView = findViewById(R.id.progressBarGameTimer);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvPoints = findViewById(R.id.tvPoints);
        tvTimer = findViewById(R.id.tvTimer);
        tvUsername = findViewById(R.id.tvUsername);

        btnOpt1 = findViewById(R.id.btnOpt1);
        btnOpt2 = findViewById(R.id.btnOpt2);
        btnOpt3 = findViewById(R.id.btnOpt3);
        btnOpt4 = findViewById(R.id.btnOpt4);
    }

}
