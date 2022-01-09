package com.example.oyunlahayati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private EditText email,pass;
    private String txtmail, txtpass;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView acc;
    private static final String TAG = "LoginPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        acc = (TextView)findViewById(R.id.createacc);
        acc.setOnClickListener((View.OnClickListener) this);
        mAuth = FirebaseAuth.getInstance();
    }
    public void signIn(View w){
        txtmail = email.getText().toString();
        txtpass = pass.getText().toString();
        mAuth.signInWithEmailAndPassword(txtmail, txtpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            updateUI(mUser);
                            Intent intent = new Intent(LoginPage.this, CategoryActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }


                });

    }
    private void updateUI(FirebaseUser user) {
    }

    private void createClick(){
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        createClick();
    }
}