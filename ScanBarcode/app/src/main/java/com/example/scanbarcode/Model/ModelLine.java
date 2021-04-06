package com.example.scanbarcode.Model;

import androidx.annotation.NonNull;

public class ModelLine {
    String SequenceNo;
    String Line_No;

    public ModelLine(String SequenceNo, String Line_No) {
        this.SequenceNo = SequenceNo;
        this.Line_No = Line_No;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String SequenceNo) {
        this.SequenceNo = SequenceNo;
    }

    public String getLine_No() {
        return Line_No;
    }

    public void setLine_No(String Line_No) {
        this.Line_No = Line_No;
    }

    @NonNull
    @Override
    public String toString() {
        return getLine_No();
    }
}
