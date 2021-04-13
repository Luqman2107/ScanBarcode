package com.dung.gedung.Model;

import androidx.annotation.NonNull;

public class ModelKota {
    String id_kota;
    String nama_kota;

    public ModelKota(String id_kota, String nama_kota) {
        this.id_kota = id_kota;
        this.nama_kota = nama_kota;
    }

    public String getId_kota() {
        return id_kota;
    }

    public void setId_kota(String id_kota) {
        this.id_kota = id_kota;
    }

    public String getNama_kota() {
        return nama_kota;
    }

    public void setNama_kota(String nama_kota) {
        this.nama_kota = nama_kota;
    }

    @NonNull
    @Override
    public String toString() {
        return getNama_kota();
    }
}
