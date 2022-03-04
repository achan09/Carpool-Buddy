package com.example.carpoolbuddy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;

public class AuthenticationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailField = findViewById(R.id.emailEditText);
        passwordField = findViewById(R.id.passwordEditText);
    }

    public void signUp(View v)
    {
        System.out.println("Sign up");
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        User newUser = new User(emailString); //create new user
        firestore.collection("/users").document(emailString).set(newUser);

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("SIGN UP", "successfully signed up user");
                            sendToast("sign up successful");

                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                            //show success message toast
                        }
                        else
                        {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            sendToast("signup failed");
                            updateUI(null);
                        }
                    }
                });

    }

    public void logIn(View v)
    {
        System.out.println("Log in");
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        System.out.println(String.format("email: %s and password: %s", emailString, passwordString));

        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("LOG IN", "successfully logged in user");
                            sendToast("user logged in");

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            Log.w(TAG, "logInUserWithEmail:failure", task.getException());
                            sendToast("user login failed");
                            updateUI(null);
                        }
                    }
                });
    }


    public void updateUI(FirebaseUser currentUser)
    {
        if(currentUser != null)
        {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void sendToast(String message)
    {
        Toast.makeText(AuthenticationActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}