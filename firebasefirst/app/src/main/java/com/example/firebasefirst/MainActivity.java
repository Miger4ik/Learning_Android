package com.example.firebasefirst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.firebasefirst.models.User;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn;
    private Button btnRegister;

    // реєстрація
    private FirebaseAuth auth;
    // робота з бд
    private FirebaseDatabase db;
    // робота з табличкою в бд
    private DatabaseReference users;

    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllView();

        startFirebase();

        setOnClickToButtons();

    }

    private void findAllView() {
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        root = findViewById(R.id.root_element);
    }

    private void startFirebase() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
    }

    private void setOnClickToButtons () {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });
    }

    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Войти");
        dialog.setMessage("Введите все дание для входа");

        LayoutInflater inflater = LayoutInflater.from(this);

        View signInWindow = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(signInWindow);

        final MaterialEditText email = signInWindow.findViewById(R.id.emailField);
        final MaterialEditText password = signInWindow.findViewById(R.id.passField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length() < 5) {
                    Snackbar.make(root, "Введите пароль, которий более 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(MainActivity.this, MapActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(root, "Ошибка Входа. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрироваться");
        dialog.setMessage("Введите все дание для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View registerWindow = inflater.inflate(R.layout.register_window, null);
        dialog.setView(registerWindow);

        final MaterialEditText email = registerWindow.findViewById(R.id.emailField);
        final MaterialEditText password = registerWindow.findViewById(R.id.passField);
        final MaterialEditText name = registerWindow.findViewById(R.id.nameField);
        final MaterialEditText phone = registerWindow.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Принять", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(root, "Введите ваш телефон", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 5) {
                    Snackbar.make(root, "Введите пароль, которий более 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // реєстрація користувача
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // якщо користувача зареєструвало
                                // додаємо в базу даних всю його інформацію

                                // створюємо об'єкт користувача
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setName(name.getText().toString());
                                user.setPass(password.getText().toString());
                                user.setPhone(phone.getText().toString());

                                // ключ для користувача вказуємо за допомогою ".child(...)"
                                // далі додаємо об'єкт користувача в базу даних в табличку users ".setValue(user)"
                                // при вдалому добавленні користувача в базу показуємо повідомлення ".addOnSuccessListener..."
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(root, "Пользователь добавлен", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(root, "Ошибка регистрации. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                Log.d("register_error", "onFailure");
                            }
                        });
            }
        });

        dialog.show();
    }
}