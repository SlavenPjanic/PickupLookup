package com.example.pickuplookup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button buttonLogin;

    String username, password;

    DBHelper dbHelper = new DBHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setEnabled(false);

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateFeilds();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateFeilds();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void validateFeilds()
    {
        Log.d("LOGIN","click");
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if(!username.equals("") && !password.equals(""))
        {
            Log.d("LOGIN","enable");
            buttonLogin.setEnabled(true);
        }

        else
        {
            Log.d("LOGIN","disable");
            buttonLogin.setEnabled(false);
        }
    }

    public void onLogin(View view) {

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        //for profile testing login advances from login no matter the text entered
        //dbHelper.logIn(username, password);

        dbHelper.getDb().collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size() == 1)
                            {

                                LoggedIn();
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"Error Connecting to Database", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void OnSignup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    void LoggedIn()
    {
        Toast.makeText(getApplicationContext(),"Logged In", Toast.LENGTH_LONG).show();
        finish();

        Intent intent = new Intent(this, NavActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
