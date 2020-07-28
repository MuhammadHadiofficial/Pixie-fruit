package com.example.pixiefruit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixiefruit.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Button signUpButton;
    TextView loginUI;
    FirebaseAuth firebaseAuth;
    EditText emailUI;
    EditText nameUI;
    EditText passwordUI;
    EditText numberUI;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onStart() {
        super.onStart();
    if(firebaseAuth.getCurrentUser()!=null){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);

    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent= new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);

        }

        firebaseAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
    emailUI=findViewById(R.id.email);
    nameUI=findViewById(R.id.name);
    passwordUI=findViewById(R.id.password);
    nameUI=findViewById(R.id.name);
        signUpButton = findViewById(R.id.signupButton);
    signUpButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
        registerUser();
        }
    });

    loginUI=findViewById(R.id.backLogin);
    loginUI.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(SignUpActivity.this,SignIn.class);
            startActivity(intent);
        }
    });
    }
    private void registerUser(){
        final String email=this.emailUI.getText().toString();
        final String password=this.passwordUI.getText().toString();
        final String name=this.nameUI.getText().toString();
        final String phone=this.passwordUI.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, ""+firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

                    User user= new User(
                      email,
                      name,
                      phone
                    );
                    db.collection("users").add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    Toast.makeText(SignUpActivity.this, "added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });


                }else {
                    progressBar.setVisibility(View.GONE);
                    AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                    alert.setTitle("Registration failed.");
                    alert.setNegativeButton("Cancel", null);
                    AlertDialog dialog = alert.create();
                    dialog.show();
                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
