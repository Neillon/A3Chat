package com.neillon.a3chat.Firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyDatabaseTest {

    @Test
    public void getDatabase() {
    }

    @Test
    public void saveUser() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        Query query = mDatabase.child("rayller").orderByChild("name");
        Log.i("return query", query.toString());
        assertEquals("rayller", query.toString());
    }
}