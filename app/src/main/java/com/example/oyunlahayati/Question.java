package com.example.oyunlahayati;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Question extends AppCompatActivity implements View.OnClickListener {

    private TextView question, qCount;
    private Button option1, option2, option3, option4;
    private List<QuestionClass> questionClassList;
    int quesNum;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = findViewById(R.id.question);
        qCount = findViewById(R.id.qnum);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        getQuestionList();

        score = 0;

    }

    private void getQuestionList() {
        questionClassList = new ArrayList<>();

        questionClassList.add(new QuestionClass("Question1", "A", "B", "C", "D", 2));
        questionClassList.add(new QuestionClass("Question2", "A", "B", "C", "D", 2));
        questionClassList.add(new QuestionClass("Question3", "A", "B", "C", "D", 2));
        questionClassList.add(new QuestionClass("Question4", "A", "B", "C", "D", 2));
        questionClassList.add(new QuestionClass("Question5", "A", "B", "C", "D", 2));

        setQuestion();

    }

    private void setQuestion() {

        question.setText(questionClassList.get(0).getQuestion());
        option1.setText(questionClassList.get(0).getOptionA());
        option2.setText(questionClassList.get(0).getOptionB());
        option3.setText(questionClassList.get(0).getOptionC());
        option4.setText(questionClassList.get(0).getOptionD());

        qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionClassList.size()));

        quesNum = 0;

    }

    @Override
    public void onClick(View view) {

        int selectedOption = 0;

        switch (view.getId()) {
            case R.id.option1:
                selectedOption = 1;
                break;
            case R.id.option2:
                selectedOption = 2;
                break;
            case R.id.option3:
                selectedOption = 3;
                break;
            case R.id.option4:
                selectedOption = 4;
                break;

            default:
        }
        checkAnswer(selectedOption, view);
    }

    private void checkAnswer(int selectedOption, View view) {
        if (selectedOption == questionClassList.get(quesNum).getCorrectAnswer()) {
            //right answer
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        } else {
            //wrong answer
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionClassList.get(quesNum).getCorrectAnswer()) {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;

            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 1000);
    }

    private void changeQuestion() {
        if (quesNum < questionClassList.size() - 1) {

            quesNum++;

            playAnim(question, 0, 0);
            playAnim(option1, 0, 1);
            playAnim(option2, 0, 2);
            playAnim(option3, 0, 3);
            playAnim(option4, 0, 4);

            qCount.setText(String.valueOf(quesNum + 1) + "/" + String.valueOf(questionClassList.size()));

        } else {
            // go to Score Activity
            Intent intent = new Intent(Question.this, Score.class);
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionClassList.size()));
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Question.this.finish();
        }
    }

    private void playAnim(View view, final int value, int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (value == 0) {
                    switch (viewNum) {
                        case 0:
                            ((TextView) view).setText(questionClassList.get(quesNum).getQuestion());
                            break;
                        case 1:
                            ((Button) view).setText(questionClassList.get(quesNum).getOptionA());
                            break;
                        case 2:
                            ((Button) view).setText(questionClassList.get(quesNum).getOptionB());
                            break;
                        case 3:
                            ((Button) view).setText(questionClassList.get(quesNum).getOptionC());
                            break;
                        case 4:
                            ((Button) view).setText(questionClassList.get(quesNum).getOptionD());
                            break;
                    }
                    if (viewNum != 0) {
                        ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00BFA6")));
                    }
                    playAnim(view, 1, viewNum);

                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}