package com.example.authentification123;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private TextView gotosignup;
    private EditText emailSignIn, passwordSignin;
    private Button btnSignIn;
    private CheckBox remembermeSignIn;
    private String emailInput, passwordInput;
    private TextView gotoforgetpass;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        gotosignup = findViewById(R.id.gotoSignUp);
        emailSignIn = findViewById(R.id.emailSignIn);
        passwordSignin = findViewById(R.id.passwordSignIn);
        remembermeSignIn = findViewById(R.id.rememberMeSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        gotoforgetpass = findViewById(R.id.gotoForgetPassword);


        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {
            Intent intent = new Intent(SignInActivity.this, ProfilActivity.class);
            startActivity(intent);
        } else if ((checkbox.equals("false"))) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }

        gotosignup.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

        gotoforgetpass.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, ForgetPassActivity.class));
        });

        btnSignIn.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait ... ");
            if (validate()) {
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        checkEmailVerification();
                    } else {
                        Toast.makeText(this, "error !!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        remembermeSignIn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("remember", "true");
                editor.apply();
            } else if (!buttonView.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("remember", "false");
                editor.apply();
            }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();
        if (loggedUser != null) {
            if (loggedUser.isEmailVerified()) {
                startActivity(new Intent(SignInActivity.this, ProfilActivity.class));
                progressDialog.dismiss();
                finish();
            } else {
                Toast.makeText(this, "Please verify your email !!", Toast.LENGTH_SHORT).show();
                loggedUser.sendEmailVerification();
                firebaseAuth.signOut();
                progressDialog.dismiss();
            }
        }
    }

    private boolean validate() {
        boolean result = false;
        emailInput = emailSignIn.getText().toString().trim();
        passwordInput = passwordSignin.getText().toString().trim();

        if (!isValidPattern(emailInput, EMAIL_PATTERN)) {
            emailSignIn.setError("email is invalide");
        } else if (passwordInput.length() < 8) {
            passwordSignin.setError("Password must be at least 8 characters long. !!!");
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