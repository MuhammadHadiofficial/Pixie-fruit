package com.example.pixiefruit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pixiefruit.Model.Counterizer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Results extends AppCompatActivity {
    private BarChart barChart;
    Button home;
    TextView count1,count2,count3,count4,totalavg,predict,avg1,avg2,avg3,avg4,totaltrees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setBarchart();
        setLinechart();
        count1=findViewById(R.id.count1);
        count2=findViewById(R.id.count2);
        count3=findViewById(R.id.count3);
        count4=findViewById(R.id.count4);
        avg1=findViewById(R.id.avg1);
        avg2=findViewById(R.id.avg2);
        avg3=findViewById(R.id.avg3);
        avg4=findViewById(R.id.avg4);
        totalavg=findViewById(R.id.totalavg);
        predict=findViewById(R.id.weighted);
        totaltrees=findViewById(R.id.totaltrees);
        int count1num=Counterizer.getInstance().get(0).getContourList()+Counterizer.getInstance().get(1).getContourList();
        int count2num=Counterizer.getInstance().get(2).getContourList()+Counterizer.getInstance().get(3).getContourList();
        int count3num=Counterizer.getInstance().get(4).getContourList()+Counterizer.getInstance().get(5).getContourList();
        int count4num=Counterizer.getInstance().get(6).getContourList()+Counterizer.getInstance().get(7).getContourList();
        int avg1num=count1num/4;
        int avg2num=count2num/4;
        int avg3num=count3num/4;
        int avg4num=count4num/4;
        avg1.setText(String.valueOf(avg1num));
        avg2.setText(String.valueOf(avg2num));
        avg3.setText(String.valueOf(avg3num));
        avg4.setText(String.valueOf(avg4num));
        int totalaverage=avg1num+avg2num+avg3num+avg4num;
        totalavg.setText(String.valueOf(totalaverage));
        Intent intent=getIntent();

        int total=totalaverage*Integer.parseInt(intent.getStringExtra("trees"));
        totaltrees.setText(intent.getStringExtra("trees"));
        predict.setText(String.valueOf(total));
        Toast.makeText(this, intent.getStringExtra("trees"), Toast.LENGTH_SHORT).show();
        count1.setText(String.valueOf(count1num));
        count2.setText(String.valueOf(count2num));
        count3.setText(String.valueOf(count3num));
        count4.setText(String.valueOf(count4num));

        home = findViewById(R.id.cirSave);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Results.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList getData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        return entries;
    }

    private void setBarchart() {
        barChart = findViewById(R.id.barChart);
        BarDataSet barDataSet = new BarDataSet(getData(), "Tree Count");
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.getAxisRight().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] count = new String[]{"Tree 1", "Tree 2", "Tree 3", "Tree 4"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(count);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

    private void setLinechart() {
        barChart = findViewById(R.id.barChart2);
        BarDataSet barDataSet = new BarDataSet(getData(), "Tree Count");
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.getAxisRight().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] count = new String[]{"Tree 1", "Tree 2", "Tree 3", "Tree 4"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(count);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

}
