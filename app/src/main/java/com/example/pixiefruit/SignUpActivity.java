package com.example.pixiefruit;

import android.app.ProgressDialog;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Button signUpButton;
    TextView loginUI;
    FirebaseAuth firebaseAuth;
    EditText emailUI;
    EditText nameUI;
    EditText passwordUI;
    EditText areaOwnedUI;
    EditText addressUI;
    EditText numberUI;
    boolean error=false;

    ProgressDialog progressDialog;

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

        numberUI=findViewById(R.id.number);

        areaOwnedUI=findViewById(R.id.areaOwned);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setCancelable(false);
        addressUI=findViewById(R.id.adress);

        signUpButton = findViewById(R.id.signupButton);

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    signUpButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            error=false;
            if(emailUI.getText().toString().trim().isEmpty()){
            emailUI.setError("Email is required");

           error=true;

                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }else {
                emailUI.setError(null);
            }
            if(nameUI.getText().toString().trim().isEmpty()){
                nameUI.setError("Name is required");

                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                error=true;

              }else {
                nameUI.setError(null);
            }
            if (!emailUI.getText().toString().matches(emailPattern)){
                error=true;

                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                emailUI.setError("Please verify email format");

            }else {
                emailUI.setError(null);
            }
            if(areaOwnedUI.getText().toString().trim().isEmpty()){
                error=true;

                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                areaOwnedUI.setError("Area is required");
            }else {
                areaOwnedUI.setError(null);
            }
            if(addressUI.getText().toString().trim().isEmpty() || addressUI.getText().toString().trim().length()<10 ){
                error=true;

                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                addressUI.setError("Adress is required && Must be greater than 10 characters");
            }else {
                addressUI.setError(null);
            }
            if(numberUI.getText().toString().trim().isEmpty()){
                error=true;

                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                numberUI.setError("Phone number is required");
            }else {
                numberUI.setError(null);
            }
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(passwordUI.getText().toString().trim());
            if(!matcher.matches() && passwordUI.getText().toString().trim().length()<5){
                error=true;

                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                passwordUI.setError("Must have 1 Uppercase, 1 Number, 1 Symbol");
            }else{
                passwordUI.setError(null);

            }

            Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            if(error){
                Snackbar.make(view,"Please make sure all fields are filled with valid information",500);
            }else{
                progressDialog.show();
                progressDialog.setMessage("Registering");

                registerUser();
            }

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
        final String phone=this.numberUI.getText().toString();
        final String areaOwnedtext=this.areaOwnedUI.getText().toString();
        final String addresstext=this.addressUI.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, ""+firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

                    User user= new User(
                      email,
                      name,
                      phone,
                            areaOwnedtext,
                            addresstext,
                            "0",
                            "0"
                    );
                     FirebaseDatabase.getInstance().getReference("users")
                     .child(firebaseAuth.getCurrentUser().getUid())
                     .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 progressBar.setVisibility(View.GONE);
                                 progressDialog.dismiss();

                                 Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                 startActivity(intent);
                             }else{
                                 progressDialog.dismiss();

                                 AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                                 alert.setTitle("Registration failed.");
                                 alert.setNegativeButton("Cancel", null);
                                 AlertDialog dialog = alert.create();
                                 dialog.show();
                                 Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                 Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });



                }else {
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();

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
