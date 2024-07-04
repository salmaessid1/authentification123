package com.example.authentification123;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sign_UpActivity extends AppCompatActivity {

    private TextView gotoSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        gotoSignIn = findViewById(R.id.gotoSignIn);
        gotoSignIn.setOnClickListener(v ->{

            startActivity(new Intent(Sign_UpActivity.this,Sign_InActivity.class));
        });

    }
}