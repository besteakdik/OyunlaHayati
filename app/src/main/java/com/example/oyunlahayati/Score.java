package com.example.oyunlahayati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Score extends AppCompatActivity {

    private TextView score;
    private Button btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = findViewById(R.id.score);
        btnMain = (Button) findViewById(R.id.btnMain);

        String score_str = getIntent().getStringExtra("SCORE");
        score.setText(score_str);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Score.this, CategoryActivity.class);
                startActivity(intent);

            }
        });

    }
}