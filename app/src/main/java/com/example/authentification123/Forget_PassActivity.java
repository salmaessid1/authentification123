package com.example.authentification123;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Forget_PassActivity extends AppCompatActivity {

    private Button goToSignInPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);
        goToSignInPass = findViewById(R.id.gotoSignInforgetpass);
        goToSignInPass.setOnClickListener(v -> {

            startActivity(new Intent(Forget_PassActivity.this, Sign_InActivity.class));
        });
    }
}