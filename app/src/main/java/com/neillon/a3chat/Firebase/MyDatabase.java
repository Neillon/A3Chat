package com.neillon.a3chat.Firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabase {

    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;

    public static DatabaseReference getDatabase(){
        return mDatabase;
    }

}
