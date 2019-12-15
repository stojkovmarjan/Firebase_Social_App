package com.example.firebasesocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class SocialMediaActivity extends AppCompatActivity {

    ImageView ivPlaceholder;
    EditText etDescription;
    Button btnCreatePost;
    ListView listUsers;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        mAuth=FirebaseAuth.getInstance();

        ivPlaceholder=findViewById(R.id.ivPlaceholder);
        btnCreatePost=findViewById(R.id.btnCreatePost);
        etDescription=findViewById(R.id.etDescription);
        listUsers=findViewById(R.id.listUsers);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivPlaceholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case (R.id.logOutItem):
                logOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logOut();
    }

    void logOut(){
        mAuth.signOut();
        finish();
    }

}
