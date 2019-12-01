package com.neillon.a3chat.configuration;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseConfiguration {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static DatabaseReference getReference() {
        return database.getReference();
    }
}
