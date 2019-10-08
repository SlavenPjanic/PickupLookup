package com.example.pickuplookup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper();

    TextView tvName, tvBio, tvAge, tvGender, tvSports, tvSkill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        tvName = findViewById(R.id.textViewName);
        tvBio = findViewById(R.id.textViewBio);
        tvAge = findViewById(R.id.textViewAge);
        tvGender = findViewById(R.id.textViewGender);
        tvSports = findViewById(R.id.textViewSports);
        tvSkill = findViewById(R.id.textViewSkill);


        dbHelper.getDb().collection("users")
                .whereEqualTo("username", "bugsbunny5")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size() == 1)
                            {
                                for (DocumentSnapshot document: task.getResult()) {
                                    tvName.setText(getResources().getString(R.string.name) + ": " + document.getString("firstname")
                                            + " " + document.getString("lastname") );
                                    tvBio.setText(getResources().getString(R.string.bio) + ": " + document.getString("bio") );
                                    tvAge.setText(getResources().getString(R.string.age) + ": " + document.getString("age"));
                                    tvGender.setText(getResources().getString(R.string.gender) + ": " + document.getString("gender"));

                                    List<String> group = (List<String>) document.get("sports");

                                    String allSports = "";

                                    for (int i = 0; i < group.size(); i++)
                                    {
                                        allSports += group.get(i) + " ";
                                    }

                                    Log.d("Profile", allSports);
                                    Log.d("Profile", document.getString("gender"));
                                    tvSports.setText(getResources().getString(R.string.sports) + ": " + allSports);
                                    tvSkill.setText(getResources().getString(R.string.skill) + ": " + document.getString("skill"));
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"Error Connecting to Database", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void onUpdate(View view) {
        Intent intent = new Intent(this, ProfileUpdateActivity.class);
        intent.putExtra("username", getIntent().getExtras().getString("username"));
        startActivity(intent);
    }
}
