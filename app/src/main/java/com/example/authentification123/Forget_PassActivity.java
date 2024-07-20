package com.example.authentification123;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Forget_PassActivity extends AppCompatActivity {

    private Button btnback, btnsend;
    private EditText emailForgetPass;
    private String emailInput;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);
        btnback = findViewById(R.id.btnBack);
        emailForgetPass = findViewById(R.id.emailforgetpassword);
        btnsend = findViewById(R.id.btnSend);
        btnback.setOnClickListener(v -> {

            startActivity(new Intent(Forget_PassActivity.this, Sign_InActivity.class));
        });
        btnsend.setOnClickListener(v ->{

            if (validate()){
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean validate(){


        boolean result = false;
        emailInput = emailForgetPass.getText().toString().trim();
       if (!isValidPattern(emailInput, EMAIL_PATTERN)){
            emailForgetPass.setError("email is invalide");
        } else
            result = true;
        return result;

    }
    private boolean isValidPattern(String mot, String patternn) {
        Pattern pattern = Pattern.compile(patternn);
        Matcher matcher = pattern.matcher(mot);
        return matcher.matches();
    }
}