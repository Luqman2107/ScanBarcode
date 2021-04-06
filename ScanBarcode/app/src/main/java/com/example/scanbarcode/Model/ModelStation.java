package com.example.scanbarcode.Model;

import androidx.annotation.NonNull;

public class ModelStation {
    String SequenceNo;
    String Station_Name;

    public ModelStation(String SequenceNo, String Station_Name) {
        this.SequenceNo = SequenceNo;
        this.Station_Name = Station_Name;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String SequenceNo) {
        this.SequenceNo = SequenceNo;
    }

    public String getStation_Name() {
        return Station_Name;
    }

    public void setStation_Name(String Station_Name) {
        this.Station_Name = Station_Name;
    }

    @NonNull
    @Override
    public String toString() {
        return getStation_Name();
    }
}
