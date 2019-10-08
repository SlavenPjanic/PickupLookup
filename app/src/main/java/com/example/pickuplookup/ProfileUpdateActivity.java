package com.example.pickuplookup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileUpdateActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper();

    RadioButton rbMale, rbFemale, rbBeginner, rbAmateur, rbExpert;
    //RadioGroup rgGender, rgSkill;
    CheckBox cbBaseball, cbBasketball,cbFlagFootball, cbHockey, cbSoccer, cbOther;
    Spinner spBaseball;
    EditText edBio, edAge;
    Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        rbMale = findViewById(R.id.radioButtonMale);
        rbFemale = findViewById(R.id.radioButtonFemale);
        rbBeginner = findViewById(R.id.radioButtonBeginner);
        rbAmateur = findViewById(R.id.radioButtonAmateur);
        rbExpert = findViewById(R.id.radioButtonExpert);
        cbBaseball = findViewById(R.id.checkBoxBaseball);
        cbBasketball = findViewById(R.id.checkBoxBasketball);
        cbFlagFootball = findViewById(R.id.checkBoxFlagFootball);
        cbHockey = findViewById(R.id.checkBoxHockey);
        cbSoccer = findViewById(R.id.checkBoxSoccer);
        cbOther = findViewById(R.id.checkBoxOther);
        edBio = findViewById(R.id.editTextBio);
        edAge = findViewById(R.id.editTextAge);
        buttonSave = findViewById(R.id.buttonSave);

        rbMale.setChecked(true);

        //rgGender = findViewById(R.id.radioGroupGender);
        //rgSkill = findViewById(R.id.radioGroupSkill);

//        spBaseball = findViewById(R.id.spinnerBaseball);
//
//        String[] skills = {getResources().getString(R.string.beginner),
//                        getResources().getString(R.string.amateur),
//                        getResources().getString(R.string.expert)};
//
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, skills);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spBaseball.setAdapter(spinnerAdapter);
    }

    public void onSave(View view) {

        String gender = (rbMale.isChecked()) ? "Male" : "Female";

        ArrayList<String> sports = new ArrayList<>();

        if(cbBaseball.isChecked())
        {
            sports.add(cbBaseball.getText().toString());
        }

        if(cbBasketball.isChecked())
        {
            sports.add(cbBasketball.getText().toString());
        }

        if(cbFlagFootball.isChecked())
        {
            sports.add(cbFlagFootball.getText().toString());
        }

        if(cbHockey.isChecked())
        {
            sports.add(cbHockey.getText().toString());
        }

        if(cbSoccer.isChecked())
        {
            sports.add(cbSoccer.getText().toString());
        }

        if(cbOther.isChecked())
        {
            sports.add(cbOther.getText().toString());
        }

        String skill = "Beginner";

        if(rbBeginner.isChecked())
       {
            skill = rbBeginner.getText().toString();
        }
        else if(rbAmateur.isChecked())
        {
            skill = rbAmateur.getText().toString();
        }

        else if(rbExpert.isChecked())
        {
            skill = rbExpert.getText().toString();
        }

        dbHelper.getDb().collection("users")
                .document(getIntent().getExtras().getString("username"))
                .update("bio", edBio.getText().toString(),
                        "age", edAge.getText().toString(),
                        "gender", gender,
                        "sports", sports,
                        "skill", skill)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onSaveSucessful();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error Connecting to Database", Toast.LENGTH_LONG).show();
                    }
                });


    }

    void onSaveSucessful()
    {
        Toast.makeText(getApplicationContext(),"Profile Updated", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ProfileViewActivity.class);
        intent.putExtra("username", getIntent().getExtras().getString("username"));
        startActivity(intent);
    }
}
