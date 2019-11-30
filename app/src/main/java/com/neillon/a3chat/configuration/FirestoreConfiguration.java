package com.neillon.a3chat.configuration;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreConfiguration {

    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static FirebaseFirestore getInstance() {
        return firebaseFirestore;
    }

    public static CollectionReference getCollection(String collectionName) {
        if (collectionName.isEmpty())
            throw new IllegalArgumentException("collectionName is required");

        return firebaseFirestore.collection(collectionName);
    }

}
