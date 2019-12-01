package com.neillon.a3chat.configuration;

import com.google.firebase.storage.FirebaseStorage;

public class FirestorageConfiguration {
    private static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public static FirebaseStorage getInstance() {
        return firebaseStorage;
    }
}