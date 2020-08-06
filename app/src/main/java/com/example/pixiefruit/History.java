package com.example.pixiefruit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.pixiefruit.Model.HistoryInstance;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class History extends AppCompatActivity {

    private LineChart lineChart;
    private List<HistoryInstance> historyList = new ArrayList<>();
    private DataTable dataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
//        setLinechart();
        dataTable = findViewById(R.id.data_table);
        final DataTableHeader header = new DataTableHeader.Builder()
                .item("Date", 1)
                .item("Yield", 1)
                .build();
        final ArrayList<DataTableRow> rows = new ArrayList<>();
        // define 200 fake rows for table
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            // No session user
            return;
        }
        String userId = user.getUid();
        Log.e("Data", userId);
//Example you need save a Store in
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference stores = database.getReference("history/" + userId);

        stores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("Get Data", dataSnapshot.toString());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    HistoryInstance history = postSnapshot.getValue(HistoryInstance.class);
                    historyList.add(history);
                    Log.e("Get Data", history.getDate());
                    DataTableRow row = new DataTableRow.Builder()
                            .value(history.getDate())
                            .value(String.valueOf(history.getValue()))

                            .build();
                    rows.add(row);


                }
                setLinechart();
//        dataTable.setTypeface(typeface);
                dataTable.setHeader(header);
                dataTable.setRows(rows);
                dataTable.inflate(getApplicationContext());
                //                System.out.println(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private ArrayList getDataLine() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < historyList.size(); i++) {
            entries.add(new Entry(i, historyList.get(i).getValue()));
        }
        return entries;
    }

    private void setLinechart() {
        lineChart = findViewById(R.id.barChart2);
        LineDataSet linedata1 = new LineDataSet(getDataLine(), "Tree Count");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(linedata1);
        LineData data = new LineData(dataSets);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] count = new String[]{"Tree 1", "Tree 2", "Tree 3", "Tree 4"};
        ArrayList<String> list = new ArrayList<>();
        for (HistoryInstance history : historyList) {
            list.add(history.getDate());
        }
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(list);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        lineChart.setData(data);
        lineChart.animateXY(2000, 2000);
        lineChart.invalidate();
    }

}