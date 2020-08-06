package com.example.pixiefruit.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryInstance {
    private int value;
    private String date;

    public HistoryInstance() {
    }

    public HistoryInstance(int value) {
        Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(newDate);

        System.out.println("Submission Date: " + stringdate);
        this.date=stringdate;
        this.value = value;
    }

    public HistoryInstance(int value, String date) {
        this.value = value;
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
