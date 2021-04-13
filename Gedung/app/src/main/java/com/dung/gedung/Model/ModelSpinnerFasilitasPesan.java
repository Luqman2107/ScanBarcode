package com.dung.gedung.Model;

import androidx.annotation.NonNull;

public class ModelSpinnerFasilitasPesan {
    String id_fasilitas;
    String nama_fasilitas;

    public ModelSpinnerFasilitasPesan(String id_fasilitas, String nama_fasilitas) {
        this.id_fasilitas = id_fasilitas;
        this.nama_fasilitas = nama_fasilitas;
    }

    public String getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(String id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }

    public String getNama_fasilitas() {
        return nama_fasilitas;
    }

    public void setNama_fasilitas(String nama_fasilitas) {
        this.nama_fasilitas = nama_fasilitas;
    }

    @NonNull
    @Override
    public String toString() {
        return getNama_fasilitas();
    }
}
