package com.example.authentification123;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sign_InActivity extends AppCompatActivity {

    private TextView gotosignup;
    private TextView gotoforgetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gotosignup = findViewById(R.id.gotoSignUp);
        gotosignup.setOnClickListener( v -> {

            startActivity(new Intent(Sign_InActivity.this , Sign_UpActivity.class));

        });
        gotoforgetpass = findViewById(R.id.gotoForgetPassword);
        gotoforgetpass.setOnClickListener( v -> {

            startActivity(new Intent(Sign_InActivity.this,Forget_PassActivity.class));
        });
    }
}