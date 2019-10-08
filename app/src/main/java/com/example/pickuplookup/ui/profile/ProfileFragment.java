package com.example.pickuplookup.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pickuplookup.DBHelper;
import com.example.pickuplookup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProfileFragment extends Fragment {


    DBHelper dbHelper = new DBHelper();

    TextView tvName, tvBio, tvAge, tvGender, tvSports, tvSkill;
    String test = "ggg";
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);



        tvName = root.findViewById(R.id.textViewName);
        tvBio = root.findViewById(R.id.textViewBio);
        tvAge = root.findViewById(R.id.textViewAge);
        tvGender = root.findViewById(R.id.textViewGender);
        tvSports = root.findViewById(R.id.textViewSports);
        tvSkill = root.findViewById(R.id.textViewSkill);

        dbHelper.getDb().collection("users")
              //  .whereEqualTo("username", getView().getExtras().getString("username")) /// need to get from glopbal variable
                .whereEqualTo("username", "bugsbunny5") /// need to get from glopbal variable
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
                //            Toast.makeText(getApplicationContext(),"Error Connecting to Database", Toast.LENGTH_LONG).show();
                        }
                    }
                });



        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}