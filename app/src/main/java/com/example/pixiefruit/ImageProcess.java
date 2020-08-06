package com.example.pixiefruit;


import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pixiefruit.Model.Counterizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageProcess extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA = 2;
    ProgressBar progressBar;
    public static final String MyPREFERENCES = "MyPrefs";;
    List<Uri> imageUri;
    Button img1;
    Button img2;
    Button img3;
    Button img4;
    Button img5;
    Button img6;
    Button img7;
    Button img8;
    ImageView image1, image2, image3, image4, image5, image6, image7, image8;
    List<Uri> ImageUrilist;
    Button btngo;

    NotificationManagerCompat notificationManagerCompat;

    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_process);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        
        image1 = findViewById(R.id.img1);

        image2 = findViewById(R.id.img2);

        image3 = findViewById(R.id.img3);

        image4 = findViewById(R.id.img4);

        image5 = findViewById(R.id.img5);

        image6 = findViewById(R.id.img6);

        image7 = findViewById(R.id.img7);

        image8 = findViewById(R.id.img8);

        img1 = findViewById(R.id.btn1);
        img2 = findViewById(R.id.btn2);
        img3 = findViewById(R.id.btn3);
        img4 = findViewById(R.id.btn4);
        img5 = findViewById(R.id.btn5);
        img6 = findViewById(R.id.btn6);
        img7 = findViewById(R.id.btn7);
        img8 = findViewById(R.id.btn8);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        img7.setOnClickListener(this);
        img8.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        image7.setOnClickListener(this);
        image8.setOnClickListener(this);
        ImageUrilist = new ArrayList<>();
        progressBar = findViewById(R.id.progress);
        btngo = findViewById(R.id.btncmplete);
        btngo.setVisibility(View.INVISIBLE);
        btngo.setTranslationY(200);

    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (resultCode == Activity.RESULT_OK)
            progressBar.incrementProgressBy(100);
        if (progressBar.getProgress() == 100) {
            btngo.setVisibility(View.VISIBLE);
            btngo.animate().setDuration(1000).translationY(0);
        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri selectedImage = result.getUri();
//                ImageView imageView = findViewById(preferences.getInt("imgid", 0));
//                try {
//
//                    final InputStream imageStream = getContentResolver().openInputStream(selectedImage);
//
//                    final Bitmap selectImage = BitmapFactory.decodeStream(imageStream);
//                    Mat     src = new Mat(selectImage.getHeight(), selectImage.getWidth(), CvType.CV_8UC4);
//
//                    Utils.bitmapToMat(selectImage, src);
//
//                    // Converting the image to gray scale and
//                    // saving it in the dst matrix
//                    // Imgproc.cvtColor(, destination, Imgproc.COLOR_RGB2GRAY);
//                    Mat mask=skinDetection(src);
//
//                    Mat res=Contours(mask,src);
//
////                imageView.setImageURI(res);
//
//                    Bitmap bmp = Bitmap.createBitmap(res.cols(), res.rows(), Bitmap.Config.ARGB_8888);
//                    Utils.matToBitmap(res, bmp);
//                    imageView.setImageBitmap(bmp);
//                    ImageUrilist.add(selectedImage);
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                Uri selectedImage = data.getData();
     ImageView imageView = findViewById(preferences.getInt("imgid", 0));
                try {

                    final InputStream imageStream = getContentResolver().openInputStream(selectedImage);

                    final Bitmap selectImage = BitmapFactory.decodeStream(imageStream);
                    Mat src = new Mat(selectImage.getHeight(), selectImage.getWidth(), CvType.CV_8UC4);

                    Utils.bitmapToMat(selectImage, src);


                    Mat res = Contours(src);

//                imageView.setImageURI(res);

                    Bitmap bmp = Bitmap.createBitmap(res.cols(), res.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(res, bmp);
                    imageView.setImageBitmap(bmp);
                    ImageUrilist.add(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case CAMERA:
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ImageView imageView2 = findViewById(preferences.getInt("imgid", 0));
                imageView2.setImageBitmap(thumbnail);
                // saveImage(thumbnail);
                ImageUrilist.add(data.getData());
                break;

        }

    }


    Mat Contours(Mat src) {
        Mat blurred = new Mat();

        Imgproc.GaussianBlur(src, blurred, new Size(9, 9), 4);
        // define the upper and lower boundaries of the HSV pixel
        // intensities to be considered 'skin'
        Scalar lower = new Scalar(5, 70, 50);
        Scalar upper = new Scalar(32, 255, 255);

        // Convert to HSV
        Mat hsvFrame = new Mat(src.rows(), src.cols(), CvType.CV_8U, new Scalar(3));
        Imgproc.cvtColor(blurred, hsvFrame, Imgproc.COLOR_RGB2HSV, 3);

        // Mask the image for skin colors
        Mat skinMask = new Mat(hsvFrame.rows(), hsvFrame.cols(), CvType.CV_8U, new Scalar(3));
        Core.inRange(hsvFrame, lower, upper, skinMask);
//        currentSkinMask = new Mat(hsvFrame.rows(), hsvFrame.cols(), CvType.CV_8U, new Scalar(3));
//        skinMask.copyTo(currentSkinMask);

        // apply a series of erosions and dilations to the mask
        // using an elliptical kernel
        final Size kernelSize = new Size(3, 3);
        final Point anchor = new Point(-1, -1);
        final int iterations = 6;
        final int iterationsdialte = 3;
//
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, kernelSize);
        Imgproc.erode(skinMask, skinMask, kernel, anchor, iterations);
        Imgproc.dilate(skinMask, skinMask, kernel, anchor, iterationsdialte);

        // blur the mask to help remove noise, then apply the
        // mask to the frame
        final Size ksize = new Size(3, 3);

        Mat skin = new Mat(skinMask.rows(), skinMask.cols(), CvType.CV_8U, new Scalar(3));
//        Imgproc.GaussianBlur(skinMask, skinMask, ksize, 0);
//        Core.bitwise_and(src, src, skin, skinMask);

        Mat hierarchy = new Mat();

        List<MatOfPoint> contourList = new ArrayList<MatOfPoint>(); //A list to store all the contours

        //Converting the image to grayscale

        //finding contours
        Imgproc.findContours(skinMask, contourList, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //Drawing contours on a new image
        Mat contours = new Mat();
        contours.create(src.rows(), src.cols(), CvType.CV_8UC3);
        Random r = new Random();
        int count = 0;
        for (int i = 0; i < contourList.size(); i++) {
//            && Imgproc.contourArea(contourList.get(i)) < 15000
            if (Imgproc.contourArea(contourList.get(i)) > 300 && Imgproc.contourArea(contourList.get(i)) < 14000) {
                count = count + 1;

                Imgproc.drawContours(src, contourList, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)), -1);
            }
        }


        Toast.makeText(this, "" + count, Toast.LENGTH_LONG).show();

        //Converting Mat back to Bitmap
        Counterizer.getInstance().add(new Counterizer(count, contours, src, src, "sad"));
        Log.d("count", Counterizer.getInstance().size() + " Images");
        Log.d("count", count + " Fruits");
//        Toast.makeText(this, counterizers.toString(), Toast.LENGTH_SHORT).show();

        return src;
    }

//    public Mat skinDetection(Mat src) {
//
//        return skinMask;
//    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                pickFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedpreferences.edit();

        if (view.getId() == R.id.btn1) {
            edit.putInt("imgid", R.id.img1);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.btn2) {
            edit.putInt("imgid", R.id.img2);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.btn3) {
            edit.putInt("imgid", R.id.img3);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.btn4) {
            edit.putInt("imgid", R.id.img4);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.btn5) {
            edit.putInt("imgid", R.id.img5);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.btn6) {
            edit.putInt("imgid", R.id.img6);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.btn7) {
            edit.putInt("imgid", R.id.img7);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.btn8) {
            edit.putInt("imgid", R.id.img8);
            edit.commit();
            edit.apply();
            showPictureDialog();
        } else if (view.getId() == R.id.img1) {
            if (Counterizer.getInstance().get(0) != null) {
                Counterizer n = Counterizer.getInstance().get(0);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 0);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.img2) {
            if (Counterizer.getInstance().get(1) != null) {
                Counterizer n = Counterizer.getInstance().get(1);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 1);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.img3) {
            if (Counterizer.getInstance().get(2) != null) {
                Counterizer n = Counterizer.getInstance().get(2);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 2);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.img4) {
            if (Counterizer.getInstance().get(3) != null) {
                Counterizer n = Counterizer.getInstance().get(3);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 3);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.img5) {
            if (Counterizer.getInstance().get(4) != null) {
                Counterizer n = Counterizer.getInstance().get(4);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 4);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.img6) {
            if (Counterizer.getInstance().get(5) != null) {
                Counterizer n = Counterizer.getInstance().get(5);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 5);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.img7) {
            if (Counterizer.getInstance().get(6) != null) {
                Counterizer n = Counterizer.getInstance().get(6);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 6);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.img8) {
            if (Counterizer.getInstance().get(7) != null) {
                Counterizer n = Counterizer.getInstance().get(7);
                Intent intent = new Intent(this, DetailImage.class);

                intent.putExtra("data", 7);
                startActivity(intent);
            }

        }
    }

    public void completetrigger(View view) {

        Intent resultactivity = new Intent(this, PredictYield.class);
        PendingIntent intent = PendingIntent.getActivity(this, 0, resultactivity, 0);

        Notification notification = new NotificationCompat.Builder(this, NotificanClass.CHANNEL_ID).setSmallIcon(R.drawable.emailicon)
                .setContentTitle("Click to check results")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Provided Images has been processed ")
                        .addLine("and results are available")
                )
                .setContentText("Process Completed")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .build();
        notificationManagerCompat.notify(1, notification);
        startActivity(resultactivity);
    }

}