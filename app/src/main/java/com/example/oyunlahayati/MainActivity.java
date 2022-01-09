package com.example.oyunlahayati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    private void onClicklets(View view) {
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        onClicklets(v);
    }

}