package com.example.firebasesocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etUsername,etPassword;
    Button btnSignUp, btnSignIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);

        etEmail=findViewById(R.id.etEmail);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);

        mAuth=FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(etEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn(etEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser!=null){
            transitionToSocialMedia();

        }
    }

    void createAccount(String email, String pwd){

        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Create account OK",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

                            //pateka do "tabela"
                            DatabaseReference users= firebaseDatabase.getReference("my_users");

                            //pole od tabelata
                            users.child(task.getResult().getUser().getUid())//kako primary key
                                    .child("username")
                                    .setValue(etUsername.getText().toString());

                            transitionToSocialMedia();
                            // ima i drugi finti za referenca do tabela, pole, ili
                            // tabela/childtabela/pole...
                        } else {
                            Log.d("======", "signUpWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this,"Create account FAILED",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    void SignIn(String email,String pwd){

        mAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Signing in account OK",
                            Toast.LENGTH_SHORT).show();
                    transitionToSocialMedia();
                } else {
                    Log.d("======", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this,"Create account FAILED",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void transitionToSocialMedia(){
        Intent intent=new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }

}
