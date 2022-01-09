package com.example.oyunlahayati;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Question extends AppCompatActivity implements View.OnClickListener {

    private TextView question, qCount;
    private Button option1, option2, option3, option4;
    private List<QuestionClass> questionClassList;
    int quesNum;
    private int score;
    private FirebaseFirestore firestore;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = findViewById(R.id.question);
        qCount = findViewById(R.id.qnum);
        img = findViewById(R.id.imagez);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        firestore = FirebaseFirestore.getInstance();

        getQuestionList();

        score = 0;

    }

    private void getQuestionList() {
        questionClassList = new ArrayList<>();

        firestore.collection("QUIZ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();
                    for(QueryDocumentSnapshot doc: questions){
                        questionClassList.add(new QuestionClass(doc.getString("question"),doc.getString("A"),
                                doc.getString("B"),doc.getString("C"),doc.getString("D"),Integer.valueOf(doc.getString("Answer"))));
                    }
                    setQuestion();

                }else{
                    Toast.makeText(Question.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setQuestion() {

        question.setText(questionClassList.get(0).getQuestion());
        option1.setText(questionClassList.get(0).getOptionA());
        option2.setText(questionClassList.get(0).getOptionB());
        option3.setText(questionClassList.get(0).getOptionC());
        option4.setText(questionClassList.get(0).getOptionD());

        qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionClassList.size()));

        quesNum = 0;

        if(quesNum==0){
            img.setImageResource(R.drawable.dog);
        }
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

            if(quesNum < questionClassList.size()-1) {
                quesNum++;
                switch (quesNum) {
                    case 1:
                        img.setImageResource(R.drawable.grape);
                        break;
                    case 2:
                        img.setImageResource(R.drawable.cat);
                        break;
                    default:
                        break;

                }

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