package com.example.pixiefruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pixiefruit.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdditionalInfoActivity extends AppCompatActivity {
    EditText areaOwnedUI;
    EditText addressUI;
    EditText numberUI;
    EditText nameUI;

    FirebaseAuth firebaseAuth;
    Button signUpButton;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);
        firebaseAuth=FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressbar);
////        progressBar.setVisibility(View.INVISIBLE);
        nameUI=findViewById(R.id.name);
        numberUI=findViewById(R.id.number);

        areaOwnedUI=findViewById(R.id.areaOwned);
        progressDialog = new ProgressDialog(AdditionalInfoActivity.this);
        progressDialog.setCancelable(false);
        addressUI=findViewById(R.id.adress);

        signUpButton = findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                progressDialog.setMessage("Registering");
                User user=new User(firebaseAuth.getCurrentUser().getEmail(),firebaseAuth.getCurrentUser().getDisplayName(),
                        numberUI.getText().toString(),areaOwnedUI.getText().toString(),addressUI.getText().toString(),"0","0"
                        );
                FirebaseDatabase.getInstance().getReference("users")
                        .child(firebaseAuth.getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
//                            progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();

                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            progressDialog.dismiss();

                            AlertDialog.Builder alert = new AlertDialog.Builder(AdditionalInfoActivity.this);
                            alert.setTitle("Registration failed.");
                            alert.setNegativeButton("Cancel", null);
                            AlertDialog dialog = alert.create();
                            dialog.show();
                            Toast.makeText(AdditionalInfoActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                 Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}