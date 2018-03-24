package com.project.clapp.clapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.clapp.clapp.impl.UserFirebaseManager;

public class Authentication extends AppCompatActivity {

    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private EditText emailUser;
    private EditText passUser;
    private TextView errorMessage;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        mAuth = FirebaseAuth.getInstance();
        emailUser = findViewById(R.id.inputEmail);
        passUser = findViewById(R.id.inputPass);
        errorMessage = findViewById(R.id.errorMessage);
        imgView = findViewById(R.id.imgIcon);
        //imgView.setImageResource(R.drawable.hashsip);
    }

    public void login(View view) {
        String email = emailUser.getText().toString();
        String password = passUser.getText().toString();
        Log.d(TAG, "ola" + email);
        Log.d(TAG, "pass" + password);
        //Verifica se o utilizador existe na Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Authentication.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "PODE ENTRAR");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Envia os dados para a RealTimeDatabase do Firebase
                            UserFirebaseManager ufm = UserFirebaseManager.getInstance();
                            ufm.addUser(user.getUid(), user.getEmail());

                            //PASSAGEM PARA A HOME PAGE
                            Intent intent = new Intent(Authentication.this, Home.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "UTILIZADOR INEXISTENTE", task.getException());
                            errorMessage.setText("User does not exist");
                        }
                    }
                });
    }

    public void entrar(View view) {
        Intent intent = new Intent(Authentication.this, Home.class);
        startActivity(intent);
    }
}