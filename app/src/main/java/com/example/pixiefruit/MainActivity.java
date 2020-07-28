package com.example.pixiefruit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bgapp;
    Animation bganim, fromBottom;
    private int My_REQUEST_CODE = 101;
    //    Button showDetails;
    LinearLayout linearLayout, textHome, logout, profile, mainmenu;
    TextView text;
    LinearLayout profilebtn, algoRunbtn,classifybtn,leavesbtn;
    ProgressDialog progressDialog;
    ImageView userimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        userimg = findViewById(R.id.userimgs);
//
//        Glide
//                .with(this)
//                .load("https://firebasestorage.googleapis.com/v0/b/pixie-fruit.appspot.com/o/profile%2Fimg.jpg?alt=media&token=cece58fe-020c-4b13-890a-48a5fc2fb0ce")
//               .placeholder(R.drawable.boy) .centerCrop()
//                .into(userimg);
        //   Glide.with(getApplicationContext()).load(").into(userimg);
//showDetails=findViewById(R.id.btn_sign_out);
//showDetails.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.menu)

                .inject();
        algoRunbtn = findViewById(R.id.algoRunbtn);
        algoRunbtn.setOnClickListener(this);

        classifybtn=findViewById(R.id.classifybtn);
        classifybtn.setOnClickListener(this);

        leavesbtn=findViewById(R.id.leavesbtn);
        leavesbtn.setOnClickListener(this);
        bgapp = findViewById(R.id.bgapp);
//        bganim= AnimationUtils.loadAnimation(this,R.anim.bganim);
//        bgapp.startAnimation(bganim);
        bgapp.animate().translationY(-1400).setDuration(1300).setStartDelay(300);
        text = findViewById(R.id.textview);

        text.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        mainmenu = findViewById(R.id.mainmenu);
        mainmenu.animate().alpha(1).setDuration(100).setStartDelay(1300);

        linearLayout = findViewById(R.id.textSplash);
        linearLayout.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.frombottom);
        textHome = findViewById(R.id.texthomeTop);
        textHome.setVisibility(View.VISIBLE);
        textHome.animate().translationY(-500).setDuration(800).setStartDelay(500);
        logout = findViewById(R.id.menu4);
        logout.setOnClickListener(this);
        profile = findViewById(R.id.menu3);
        profile.setOnClickListener(this);
//        profilebtn=findViewById(R.id.profilebtn);
//        profilebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Affjfhgj", Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(MainActivity.this,UserProfileActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onClick(View view) {
//        AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
//        alert.setTitle(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//        alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        alert.setNegativeButton("Cancel",null);
//        AlertDialog dialog=alert.create();
//        dialog.show();


        if (view.getId() == R.id.menu4) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
        } else if (view.getId() == R.id.menu3) {
            Intent intent1 = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent1);
        } else if (view.getId() == R.id.algoRunbtn) {
            Intent intent1 = new Intent(MainActivity.this, ImageProcess.class);
            startActivity(intent1);
        }else if (view.getId() == R.id.classifybtn) {
            Intent intent1 = new Intent(MainActivity.this, ClassifierActivity.class);
            startActivity(intent1);
        }else if (view.getId() == R.id.leavesbtn) {
            Intent intent1 = new Intent(MainActivity.this, LeavesDisease.class);
            startActivity(intent1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    public void getactive(View view) {
        super.onBackPressed();

        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }


    public void getDiseaseClassifier(View view) {
        Intent intent = new Intent(MainActivity.this, ClassifierActivity.class);
        startActivity(intent);

    }
}

