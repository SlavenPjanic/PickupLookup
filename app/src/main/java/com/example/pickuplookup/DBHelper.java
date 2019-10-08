package com.example.pickuplookup;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int accountResults;

    //make db connection singleton?
    public DBHelper()
    {
    }

    public FirebaseFirestore getDb()
    {
        return db;
    }

    public void logIn(String username, String password)
    {
    }

//    public interface OnCompleteCallback{
//        void onComplete(boolean success);
//    }

//    public void accountExist(final String barcode,final OnCompleteCallback callback) {
//        DocumentReference barcodeRef = db.collection("users")
//                .document(barcode);
//
//        barcodeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    accountResults = 0;
//                    callback.onComplete(document.exists());
//                }
//            }
//        });
//    }
//    public boolean accountExist(String username)
//    {
//
//    }


    public void createAccount(final String fName,final String lName,final String email,final String username,final String password)
    {
//        ArrayList<String> test = new ArrayList<>();
//        test.add("base");
//        test.add("basket");

        CollectionReference users = db.collection("users");

        //Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("firstname", fName);
        user.put("lastname", lName);
        user.put("email", email);
        user.put("username", username);
        user.put("password", password);
        //user.put("bio", "");
        //user.put("age", "");
        //user.put("gender", "");
        //user.put("sports", "");
        //user.put("skill", "");

        users.document(username).set(user);

//        //Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        //     Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //     Log.w(TAG, "Error adding document", e);
//                    }
//                });
//
//        Log.d("DBHELPER", "result - it worked");
    }
}
