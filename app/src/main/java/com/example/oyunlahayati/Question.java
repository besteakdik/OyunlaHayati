package com.example.oyunlahayati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Question extends AppCompatActivity implements View.OnClickListener {

    private TextView question, qCount;
    private Button option1, option2, option3, option4;
    private List<QuestionClass> questionClassList;

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

    }

    @Override
    public void onClick(View view) {

        int selectedOption = 0;

        switch (view.getId()){
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
        checkAnswer(selectedOption);
    }

    private void checkAnswer(int selectedOption) {

    }
}