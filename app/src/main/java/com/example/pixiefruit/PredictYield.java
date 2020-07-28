package com.example.pixiefruit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PredictYield extends AppCompatActivity {
    private TextInputEditText treesinput;
    private Button predict_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_yield);
        treesinput = findViewById(R.id.treesinput);
        predict_btn = findViewById(R.id.predictButton);
        final Intent resultactivity = new Intent(getApplicationContext(), Results.class);
        predict_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (treesinput.getText().toString().trim()=="" || treesinput.getText().length() < 1){
                    Snackbar.make(v, "Value cannot be empty", Snackbar.LENGTH_SHORT)
                            .show();

                }else{
try{
int value= Integer.parseInt(treesinput.getText().toString());
    if (treesinput.getText().length() < 1 || value< 10) {
        Snackbar.make(v, "Value must be integer and greater than atleast 10", Snackbar.LENGTH_SHORT)
                .show();
    } else {
        Toast.makeText(PredictYield.this, ""+value, Toast.LENGTH_SHORT).show();
        resultactivity.putExtra("trees",String.valueOf(value));
        startActivity(resultactivity);
    }

}catch (Exception e){
    Snackbar.make(v, "Value must be integer", Snackbar.LENGTH_SHORT)
            .show();
}
}
            }
        });
    }

}