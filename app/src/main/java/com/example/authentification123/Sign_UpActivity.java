package com.example.authentification123;

import android.app.ProgressDialog;
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

import com.example.authentification123.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_UpActivity extends AppCompatActivity {

    //declaration de variables
    private TextView goToSignIn;
    private EditText fullName, email, cin, phone, password, confirmPassword;
    private Button btnSignUp;
    private String fullNameInput, emailInput, cinInput, phoneInput, passwordInput, confirmPasswordInput;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

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
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);



        //Actions
        goToSignIn.setOnClickListener(v -> {

            startActivity(new Intent(Sign_UpActivity.this, Sign_InActivity.class));
        });

        btnSignUp.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait ... !");
            progressDialog.show();
            if (validate()) {
                firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(Task -> {
                    if (Task.isSuccessful()) {
                        sendEmailVerification();
                    } else {
                        Toast.makeText(this, "register failed !", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }

    private void sendEmailVerification() {
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();
        if (loggedUser != null) {
            loggedUser.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    sendUserData();
                    Toast.makeText(this, "Registration done , please check your email .", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    startActivity(new Intent(Sign_UpActivity.this, Sign_InActivity.class));
                    progressDialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(this, "Registration failed !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }

    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
        User user = new User(fullNameInput, emailInput, cinInput, phoneInput);
        databaseReference.child(""+firebaseAuth.getUid()).setValue(user);


    }

    private boolean validate() {


        boolean result = false;
        fullNameInput = fullName.getText().toString().trim();
        emailInput = email.getText().toString().trim();
        cinInput = cin.getText().toString().trim();
        phoneInput = phone.getText().toString().trim();
        passwordInput = password.getText().toString().trim();
        confirmPasswordInput = confirmPassword.getText().toString().trim();

        if (fullNameInput.length() < 7) {
            fullName.setError("Fullname is invalide !!!");
        } else if (!isValidPattern(emailInput, EMAIL_PATTERN)) {
            email.setError("email is invalide");
        } else if (cinInput.length() != 8) {
            cin.setError("The ID card code must contain exactly 8 digits !!!");
        } else if (phoneInput.length() != 8) {
            phone.setError("The Phone number code must contain exactly 8 digits !!!");
        } else if (passwordInput.length() < 8) {
            password.setError("Password must be at least 8 characters long. !!!");
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            confirmPassword.setError("Passwords do not match !!!");
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