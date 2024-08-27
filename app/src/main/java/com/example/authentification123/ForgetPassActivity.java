package com.example.authentification123;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPassActivity extends AppCompatActivity {

    private Button btnback, btnsend;
    private EditText emailForgetPass;
    private String emailInput;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);
        btnback = findViewById(R.id.btnBack);
        emailForgetPass = findViewById(R.id.emailforgetpassword);
        btnsend = findViewById(R.id.btnSend);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        btnback.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPassActivity.this, SignInActivity.class));
        });

        btnsend.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait ... ");
            if (validate()) {
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(emailInput).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "password reset email has been sent", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPassActivity.this, SignInActivity.class));
                        progressDialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(this, "Server error !! ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    private boolean validate() {
        boolean result = false;

        emailInput = emailForgetPass.getText().toString().trim();
        if (!isValidPattern(emailInput, EMAIL_PATTERN)) {
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