package com.example.authentification123;

import android.content.Intent;
import android.os.Bundle;
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

public class Sign_UpActivity extends AppCompatActivity {

    //declaration de variables
    private TextView goToSignIn;
    private EditText fullName, email, cin, phone, password, confirmPassword;
    private Button btnSignUp;
    private String fullNameInput, emailInput, cinInput, phoneInput, passwordInput, confirmPasswordInput;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        // Affectation des views
        goToSignIn = findViewById(R.id.gotoSignIn);
        fullName = findViewById(R.id.fullNameSignUp);
        email = findViewById(R.id.emailSignUp);
        cin = findViewById(R.id.cinSignUp);
        phone = findViewById(R.id.phoneNumberSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmPassword = findViewById(R.id.confirmPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);

        //Actions
        goToSignIn.setOnClickListener(v -> {

            startActivity(new Intent(Sign_UpActivity.this, Sign_InActivity.class));
        });

        btnSignUp.setOnClickListener(v ->{

          if (validate()){
              Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
          }
        });

    }

    private boolean validate(){


        boolean result = false;
        fullNameInput = fullName.getText().toString().trim();
        emailInput = email.getText().toString().trim();
        cinInput = cin.getText().toString().trim();
        phoneInput = phone.getText().toString().trim();
        passwordInput = password.getText().toString().trim();
        confirmPasswordInput = confirmPassword.getText().toString().trim();
        
        if (fullNameInput.length()<7){
            fullName.setError("fullname invalide !!!");
        } else if (!isValidPattern(emailInput, EMAIL_PATTERN)){
            email.setError("email is invalide");
        }else
            result = true;
        return result;

    }
    private boolean isValidPattern(String mot, String patternn) {
        Pattern pattern = Pattern.compile(patternn);
        Matcher matcher = pattern.matcher(mot);
        return matcher.matches();
    }
}