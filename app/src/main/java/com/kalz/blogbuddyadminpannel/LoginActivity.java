package com.kalz.blogbuddyadminpannel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText;
    private EditText loginPassText;
    private Button loginBtn;
    private Button loginRegBtn;
    private ProgressBar loginProgress;
    private TextView backToMain;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        try {
            loginBtn = (Button) findViewById(R.id.login_btn);
            loginEmailText = (EditText) findViewById(R.id.reg_email);
            loginPassText = (EditText) findViewById(R.id.reg_password);
            loginProgress = (ProgressBar) findViewById(R.id.reg_progress);
            backToMain = findViewById(R.id.back_to_main_reg);
        }catch (Exception e){
            Toast.makeText(LoginActivity.this,"Error : " + e,Toast.LENGTH_LONG).show();
        }
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent firstIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(firstIntent);
                finish();


            }
        });




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String loginEmail = loginEmailText.getText().toString();
                String loginPass = loginPassText.getText().toString();

                if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)){
                    loginProgress.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Intent setupIntent = new Intent(LoginActivity.this,AllBlogsActivity.class);
                                startActivity(setupIntent);

                            }else {

                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error :"+ errorMessage, Toast.LENGTH_LONG).show();

                            }
                            loginProgress.setVisibility(View.INVISIBLE);



                        }
                    });

                }else{

                    if (TextUtils.isEmpty(loginEmail)){

                        Toast.makeText(LoginActivity.this,"Error : Enter Your Email", Toast.LENGTH_LONG).show();

                    }else if(TextUtils.isEmpty(loginPass)){

                        Toast.makeText(LoginActivity.this,"Error : Enter Your Password", Toast.LENGTH_LONG).show();

                    }else {

                        Toast.makeText(LoginActivity.this,"Error : Something goes wrong", Toast.LENGTH_LONG).show();

                    }


                }

            }
        });



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null){
//
//            sendToMain();
//
//        }
//    }
//
//    private void sendToMain() {
//
//        Intent mainIntent = new Intent(LoginActivity.this,SelecterActivity.class);
//        startActivity(mainIntent);
//        finish();

//    }

}
