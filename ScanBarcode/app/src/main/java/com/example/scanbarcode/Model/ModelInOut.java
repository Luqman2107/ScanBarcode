package com.example.scanbarcode.Model;

import androidx.annotation.NonNull;

public class ModelInOut {
    String SequenceNo;
    String IO_Name;

    public ModelInOut(String SequenceNo, String IO_Name) {
        this.SequenceNo = SequenceNo;
        this.IO_Name = IO_Name;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String SequenceNo) {
        this.SequenceNo = SequenceNo;
    }

    public String getIO_Name() {
        return IO_Name;
    }

    public void setIO_Name(String IO_Name) {
        this.IO_Name = IO_Name;
    }

    @NonNull
    @Override
    public String toString() {
        return getIO_Name();
    }
}
