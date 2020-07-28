package com.example.pixiefruit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pixiefruit.Model.Counterizer;

public class DetailImage extends AppCompatActivity {

    private SubsamplingScaleImageView img1;
    private TextView fruitCount;
    private TextView weightCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        Intent intent = getIntent();
        int data = intent.getIntExtra("data", 0);
        img1=(SubsamplingScaleImageView)findViewById(R.id.img1detail);

        fruitCount=findViewById(R.id.image1count);
        weightCount=findViewById(R.id.image2count);
        weightCount.setText(Counterizer.getInstance().get(data).getWeightedAverage()+"");
        fruitCount.setText(Counterizer.getInstance().get(data).getContourList()+"");

        img1.setImage(ImageSource.bitmap(Counterizer.convertMatToBitMap(Counterizer.getInstance().get(data).getOriginalImage())));
        Log.d("DataImage", String.valueOf(Counterizer.getInstance().get(0).getContourList()));
    }
}
