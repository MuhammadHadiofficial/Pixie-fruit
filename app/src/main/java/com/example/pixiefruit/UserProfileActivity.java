package com.example.pixiefruit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixiefruit.Model.User;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class UserProfileActivity extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "img";
    ImageView userimgbtn;
    TextView textView, profileEmail, phonenumber, address, testrunbox, totalimages, areaowned;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA = 2;

    //    PieChart pieChart;
//    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        final ProgressDialog pd = new ProgressDialog(UserProfileActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        textView = findViewById(R.id.usernamebutton);
        profileEmail = findViewById(R.id.emailbutton);
        phonenumber = findViewById(R.id.phonenumber);
        address = findViewById(R.id.addressbutton);
        testrunbox = findViewById(R.id.testrunsbox);
        totalimages = findViewById(R.id.totalimages);
        areaowned = findViewById(R.id.areaowned);
        FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid().toString())

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        textView.setText(user.getName());
                        profileEmail.setText(user.getEmail());
                        phonenumber.setText(user.getPhone());
                        address.setText(user.getAddress());
                        testrunbox.setText(user.getTestruns());
                        areaowned.setText(user.getAreaOwned()+" Acre");
                        totalimages.setText(user.getTotalimages());
                        Toast.makeText(UserProfileActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        pd.dismiss();
                    }
                });
    }
//
//    private void pickFromGallery() {
//        //Create an Intent with action as ACTION_PICK
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        // Sets the type as image/*. This ensures only components of type image are selected
//        intent.setType("image/*");
//        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
//        String[] mimeTypes = {"image/jpeg", "image/png"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        // Launching the Intent
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);
//    }
//
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Result code is RESULT_OK only if the user selects an Image
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_CANCELED) {
//            return;
//        }
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode) {
//                case GALLERY_REQUEST_CODE:
//                    //data.getData returns the content URI for the selected Image
//                    Uri selectedImage = data.getData();
//                    final Uri selectedImage2 = data.getData();
//                    StorageReference storageReference1 = storageReference.child("profile" + "/" + FirebaseAuth.getInstance().getUid() + ".jpg");
//                    storageReference1.putFile(selectedImage2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(UserProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(UserProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    userimgbtn.setImageURI(selectedImage);
//
//                    break;
//                case CAMERA:
//                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//                    userimgbtn.setImageBitmap(thumbnail);
//                    // saveImage(thumbnail);
//                    Toast.makeText(UserProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    final Uri selectedImage3 = data.getData();
//                    StorageReference storageReference2 = storageReference.child("profileuser" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
//
//                    storageReference2.putFile(selectedImage3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(UserProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                            SharedPreferences.Editor edit = sharedpreferences.edit();
//                            edit.putString("profilelink", taskSnapshot.getUploadSessionUri().toString());
//                            edit.commit();
//                            edit.apply();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(UserProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    break;
//                default:
//                    break;
//            }
//    }
//
//    private void showPictureDialog() {
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {
//                "Select photo from gallery",
//                "Capture photo from camera"};
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                pickFromGallery();
//                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//    }
//
//    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//    }
//
////    @Override
////    public void onClick(View view) {
//////
//////        if (view.getId() == R.id.userimg) {
//////            showPictureDialog();
//////        }
////    }
////
////    public String saveImage(Bitmap myBitmap) {
////        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
////        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
////        File wallpaperDirectory = new File(
////                Environment.getDataDirectory() + IMAGE_DIRECTORY);
////        // have the object build the directory structure, if needed.
////        if (!wallpaperDirectory.exists()) {
////            wallpaperDirectory.mkdirs();
////        }
////
////        try {
////            File f = new File(wallpaperDirectory, Calendar.getInstance()
////                    .getTimeInMillis() + ".jpg");
////            f.createNewFile();
////            FileOutputStream fo = new FileOutputStream(f);
////            fo.write(bytes.toByteArray());
////            MediaScannerConnection.scanFile(this,
////                    new String[]{f.getPath()},
////                    new String[]{"image/jpeg"}, null);
////            fo.close();
////            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
////
////            return f.getAbsolutePath();
////        } catch (IOException e1) {
////            e1.printStackTrace();
////        }
////        return "";
////    }

}
