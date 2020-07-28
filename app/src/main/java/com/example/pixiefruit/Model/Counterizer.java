package com.example.pixiefruit.Model;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Counterizer {
    private static List<Counterizer> instance;
    int contourList =0; //A list to store all the contours
    Mat hierarchy = new Mat();
    Mat mask;
    Mat originalImage;
    String imgName;
    int weightedAverage;

    public Counterizer() {
        this.contourList = 0;
        hierarchy = new Mat();
        mask = new Mat();
        originalImage = new Mat();
        imgName = "";

    }

    public Counterizer(int contourList, Mat hierarchy, Mat mask, Mat originalImage, String imgName) {
        this.contourList = contourList;
        this.hierarchy = hierarchy;
        this.mask = mask;
        this.originalImage = originalImage;
        this.imgName = imgName;
        this.weightedAverage=this.contourList/8;
    }

    public static List<Counterizer> getInstance() {
        if (instance == null)
            instance = new ArrayList<>();
        return instance;
    }
    public static Bitmap convertMatToBitMap(Mat input){
        Bitmap bmp = null;
        Mat rgb = new Mat();
        Imgproc.cvtColor(input, rgb, Imgproc.COLOR_BGR2RGB);

        try {
            bmp = Bitmap.createBitmap(rgb.cols(), rgb.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(rgb, bmp);
        }
        catch (CvException e){


            Log.d("Exception",e.getMessage());
        }
        return bmp;


    }
    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getContourList() {
        return contourList;
    }

    public void setContourList(int contourList) {
        this.contourList = contourList;
    }
    public int getWeightedAverage() {
        return this.weightedAverage;
    }

    public void setWeightedAverage(int contourList) {
        this.weightedAverage = contourList/8;
    }

    public Mat getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Mat hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Mat getMask() {
        return mask;
    }

    public void setMask(Mat mask) {
        this.mask = mask;
    }

    public Mat getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(Mat originalImage) {
        this.originalImage = originalImage;
    }
}
