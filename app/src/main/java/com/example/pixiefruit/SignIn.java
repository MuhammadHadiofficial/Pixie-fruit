package com.example.pixiefruit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixiefruit.Model.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_SIGNIN_CODE =100 ;
    private static final String TAG = "PixieData";
    TextView otherSignButton;
    List<AuthUI.IdpConfig> providers;

    TextView signupButton;
    EditText emailButton;
    EditText passwordButton;
    Button cirLoginButton;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent= new Intent(SignIn.this,MainActivity.class);
            startActivity(intent);
        }
        signupButton = findViewById(R.id.signup_button);
        emailButton = findViewById(R.id.editTextEmail);
        passwordButton = findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        otherSignButton=findViewById(R.id.otheroption);
        signupButton.setOnClickListener(this);
        cirLoginButton.setOnClickListener(this);
        otherSignButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


        providers= Arrays.asList(
        new AuthUI.IdpConfig.GoogleBuilder().build()
        );
emailButton.setError("Cannot be empty");
        progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setCancelable(false);
    }

private void showSignInOption(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),MY_SIGNIN_CODE);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_SIGNIN_CODE){
            IdpResponse response=IdpResponse.fromResultIntent(data);

            if(resultCode==RESULT_OK){
                progressDialog.show();
                final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                final User user = new User(
                        firebaseUser.getEmail(),
                        firebaseUser.getDisplayName(),
                        firebaseUser.getPhoneNumber(),
                        "",
                        "",
                        "",
                        ""
                );
                FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid().toString())

                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(firebaseUser.getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Intent intent = new Intent(getApplicationContext(), AdditionalInfoActivity.class);
                                                startActivity(intent);

                                                Toast.makeText(SignIn.this, "Saved", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else{
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(SignIn.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Snackbar.make(this,"Failed",200);
                            }
                        });


                Toast.makeText(this, "" + firebaseUser.toString(), Toast.LENGTH_SHORT).show();


            }
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.signup_button){
            Intent intent= new Intent(SignIn.this,SignUpActivity.class);
            startActivity(intent);

        }
        else if(view.getId()==R.id.cirLoginButton){

            progressDialog.setMessage("Logging In");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailButton.getText().toString(), passwordButton.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                progressDialog.dismiss();

                                Intent intent=new Intent(SignIn.this, MainActivity.class);
                                startActivity(intent);
                            } else {

                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignIn.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                AlertDialog.Builder alert=new AlertDialog.Builder(SignIn.this);
                                alert.setTitle("Authentication failed.");
                                alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alert.setNegativeButton("Cancel",null);
AlertDialog dialog=alert.create();
dialog.show();
                            }

                            // ...
                        }
                    });

        }
        else if (view.getId()==R.id.otheroption){
            showSignInOption();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
