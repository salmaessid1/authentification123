package com.example.authentification123;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class Sign_InActivity extends AppCompatActivity {

    private TextView gotosignup;
    private EditText emailSignIn, passwordSignin;
    private Button btnSignIn;
    private CheckBox remembermeSignIn;
    private String emailInput, passwordInput;
    private TextView gotoforgetpass;
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

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if(checkbox.equals("true")){
            Intent intent = new Intent(Sign_InActivity.this, Sign_UpActivity.class);
            startActivity(intent);
        } else if ((checkbox.equals("false"))) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();

        }
        gotosignup.setOnClickListener(v -> {

            startActivity(new Intent(Sign_InActivity.this, Sign_UpActivity.class));

        });
        gotoforgetpass = findViewById(R.id.gotoForgetPassword);
        gotoforgetpass.setOnClickListener(v -> {

            startActivity(new Intent(Sign_InActivity.this, Forget_PassActivity.class));
        });

        btnSignIn.setOnClickListener(v -> {

            if (validate()) {
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }
        });
        remembermeSignIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    Toast.makeText(Sign_InActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                } else if (!buttonView.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    Toast.makeText(Sign_InActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();

                }

            }
        });
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