package com.example.project.clapp.impl;

import com.example.project.clapp.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Afonso on 11/03/2018.
 */

public class UserFirebaseManager implements IUser {

    static UserFirebaseManager ufm = null;

    public static UserFirebaseManager getInstance() {
        if(ufm == null) {
            ufm = new UserFirebaseManager();
        }
        return ufm;
    }

    @Override
    public User getUser(String name) {
        return null;
    }

    @Override
    public void addUser(String name, String mail) {
        DatabaseReference databaseUsers;
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        String id = databaseUsers.push().getKey();
        User user = new User(id, name, mail);
        databaseUsers.child(id).setValue(user);
    }

    @Override
    public void removeUser(String name) {

    }
}