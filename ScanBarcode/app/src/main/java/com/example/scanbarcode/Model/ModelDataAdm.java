package com.example.scanbarcode.Model;

import androidx.annotation.NonNull;

public class ModelDataAdm {
    String SequenceNo;
    String Adm_Name;

    public ModelDataAdm(String SequenceNo, String Adm_Name) {
        this.SequenceNo = SequenceNo;
        this.Adm_Name = Adm_Name;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String SequenceNo) {
        this.SequenceNo = SequenceNo;
    }

    public String getAdm_Name() {
        return Adm_Name;
    }

    public void setAdm_Name(String Adm_Name) {
        this.Adm_Name = Adm_Name;
    }

    @NonNull
    @Override
    public String toString() {
        return getAdm_Name();
    }
}
