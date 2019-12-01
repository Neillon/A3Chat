package com.neillon.a3chat.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.neillon.a3chat.configuration.FirestoreConfiguration;
import java.util.ArrayList;

public class UserDao {
    public static Task<QuerySnapshot> getUsers(String nickname) {
        Query query = FirestoreConfiguration.getCollection("users").whereEqualTo("nickname", (Object) nickname);
        new ArrayList();
        return query.get();
    }
}