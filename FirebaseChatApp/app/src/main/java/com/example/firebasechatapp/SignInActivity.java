package com.example.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText nameEditText;
    private Button loginSignUpButton;
    private TextView toggleLoginSignUpTextView;
    private boolean isRegister = true;

    private FirebaseAuth mAuth;

    private FirebaseDatabase database;
    private DatabaseReference usersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());

        findAllViews();
        setOnClickListeners();

        database = FirebaseDatabase.getInstance();
        usersDatabaseReference = database.getReference().child("users");
    }

    private void findAllViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        loginSignUpButton = findViewById(R.id.loginSignUpButton);
        toggleLoginSignUpTextView = findViewById(R.id.toggleLoginSignUpTextView);

    }

    private void setOnClickListeners() {
        loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegister) {
                    signUpUserAccept();
                } else {
                    loginUserAccept();
                }
            }
        });

        toggleLoginSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegister) {
                    isRegister = false;
                    toggleLoginSignUpTextView.setText("Tap To Register");
                    loginSignUpButton.setText("Log In");
                    setLoginScreen();
                } else {
                    isRegister = true;
                    toggleLoginSignUpTextView.setText("Tap To Log In");
                    loginSignUpButton.setText("Sign Up");
                    setRegisterScreen();
                }
            }
        });
    }

    private void signUpUserAccept() {
        String userEmail = emailEditText.getText().toString().trim();
        String userPassword = passwordEditText.getText().toString().trim();
        String userName = nameEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();

       if (TextUtils.isEmpty(userEmail)) {
           Toast.makeText(SignInActivity.this, "Введіть Email", Toast.LENGTH_SHORT).show();
           return;
       }

       if (userPassword.length() < 6) {
           Toast.makeText(SignInActivity.this, "Введіть пароль не коротший 6 символів", Toast.LENGTH_SHORT).show();
           return;
       }

       if (TextUtils.isEmpty(userName)) {
           Toast.makeText(SignInActivity.this, "Введіть Ім'я", Toast.LENGTH_SHORT).show();
           return;
       }

       if (TextUtils.isEmpty(repeatPassword)) {
           Toast.makeText(SignInActivity.this, "Введіть пароль повторно", Toast.LENGTH_SHORT).show();
           return;
       }

       if (!userPassword.equals(repeatPassword)) {
           Toast.makeText(SignInActivity.this, "Паролі не збігаються", Toast.LENGTH_SHORT).show();
           return;
       }

        signUpUser(userEmail, userPassword, userName);
    }

    private void loginUserAccept() {
        String userEmail = emailEditText.getText().toString().trim();
        String userPassword = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(SignInActivity.this, "Введіть Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6) {
            Toast.makeText(SignInActivity.this, "Введіть пароль не коротший 6 символів", Toast.LENGTH_SHORT).show();
            return;
        }

        loginUser(userEmail, userPassword);
    }

    private void signUpUser(final String email, final String password, final String name) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("createUserWithEmail:", "success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    createUser(user, name);
                    updateUI(user);
                } else {
                    // handle error
                    Log.w("createUserWithEmail:", "failure");
                    // Toast.makeText(SignInActivity.this, "createUser: FAILURE", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void loginUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("signInWithEmail:", "success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // handle error
                    Log.d("signInWithEmail:", "failure");
                    Toast.makeText(SignInActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void createUser(FirebaseUser firebaseUser, String name) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(name);
        usersDatabaseReference.push().setValue(user);
    }

    private void setRegisterScreen() {
        nameEditText.setVisibility(View.VISIBLE);
        repeatPasswordEditText.setVisibility(View.VISIBLE);
        passwordEditText.setText("");
    }
    private void setLoginScreen() {
        nameEditText.setVisibility(View.GONE);
        repeatPasswordEditText.setVisibility(View.GONE);
        passwordEditText.setText("");
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignInActivity.this, UserListActivity.class);
            startActivity(intent);
        }
    }
}