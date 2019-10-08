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


public class SignupActivity extends AppCompatActivity {

    String TAG = "SIGNUP";

    EditText etFName, etLName, etEmail, etUsername,etPassword, etPasswordVerify;
    Button buttonCreatAccount;
    String fName, lName, email, username, password, passwordVerify;

    DBHelper dbHelper = new DBHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFName = findViewById(R.id.editTextFName);
        etLName = findViewById(R.id.editTextLName);
        etEmail = findViewById(R.id.editTextEmail);
        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        etPasswordVerify = findViewById(R.id.editTextPasswordVerify);
        buttonCreatAccount = findViewById(R.id.buttonCreatAccount);

        buttonCreatAccount.setEnabled(false);

        etFName.addTextChangedListener(new TextWatcher() {
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

        etLName.addTextChangedListener(new TextWatcher() {
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

        etEmail.addTextChangedListener(new TextWatcher() {
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

        etPasswordVerify.addTextChangedListener(new TextWatcher() {
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
        fName = etFName.getText().toString();
        lName = etLName.getText().toString();
        email = etEmail.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        passwordVerify = etPasswordVerify.getText().toString();

        //if passwords don't match
        if(!password.equals(passwordVerify))
        {
            etPasswordVerify.setError("Passwords don't match");
        }

        else
        {
            //if all fields aren't empty
            if(!fName.equals("") && !lName.equals("") && !email.equals("") && !username.equals("")
                    && !password.equals("") && !passwordVerify.equals(""))
            {
                buttonCreatAccount.setEnabled(true);
            }

            else
            {
                buttonCreatAccount.setEnabled(false);
            }
        }
    }

    public void OnCreateAccount(View view) {
        fName = etFName.getText().toString();
        lName = etLName.getText().toString();
        email = etEmail.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        //passwordVerify = etPasswordVerify.getText().toString();

        dbHelper.getDb().collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size() > 0)
                            {
                                etUsername.setError("Username already exists");
                            }

                            else
                            {
                                AccountCreated();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"Error Connecting to Database", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void AccountCreated()
    {
        dbHelper.createAccount(fName, lName, email, username, password);
        Toast.makeText(getApplicationContext(),"Account Created", Toast.LENGTH_LONG).show();
        finish();

        Intent intent = new Intent(this, ProfileUpdateActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
